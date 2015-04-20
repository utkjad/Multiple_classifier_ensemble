package Ensemble;

import java.util.concurrent.TimeUnit;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.core.SelectedTag;

public class MajorityVote {

	public void MajorityVotePrediction(Instances train, Instances test) throws Exception {

		Vote objVote = new Vote();
		SelectedTag objTag = new SelectedTag(Vote.MAJORITY_VOTING_RULE, Vote.TAGS_RULES);

		objVote.setCombinationRule(objTag);
		// Classifier objNaiveBayes = new NaiveBayes();

		Classifier objNBTree = new NBTree();
		Classifier objJ48 = new J48();
		Classifier objLogistic = new Logistic();

		Classifier objRandomForest = new RandomForest();

		AttributeSelectedClassifier objASCREPTree = new AttributeSelectedClassifier();
		CfsSubsetEval objCfsSubsetEval = new CfsSubsetEval();
		objASCREPTree.setEvaluator(objCfsSubsetEval);
		GreedyStepwise objGreedyStepwiseSearch = new GreedyStepwise();
		objASCREPTree.setSearch(objGreedyStepwiseSearch);
		objASCREPTree.setClassifier(new REPTree());

		Classifier[] objClassifiers = { objNBTree, objJ48, objLogistic, objRandomForest, objASCREPTree };

		objVote.setClassifiers(objClassifiers);
		
		
		long starttime = System.currentTimeMillis();
		objVote.buildClassifier(train);
		
		Evaluation eval = new Evaluation(test);
		eval.evaluateModel(objVote, test);
        
		long stoptime = System.currentTimeMillis();
		long elapsedtime = stoptime - starttime;
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedtime);
        
		/*Writing Output*/
		System.out.println("=== Number of Training Instances ===");
		System.out.println(train.numInstances());
		System.out.println();
		
		System.out.println("=== Classifier model (full training set) ===");
		System.out.println();
		System.out.println(objVote);
		
		System.out.println("=== Time taken to Build Classifiers & Test ===");
		System.out.println();
		System.out.println(timeSeconds + " seconds");
		
		System.out.println(eval.toSummaryString("\nResults\n===============\n", true));
		
		System.out.println("For <50K " + "F-Measure :" + eval.fMeasure(0) + " Precision :" + eval.precision(0) + " Recall :" + eval.recall(0));
		System.out.println("For >50K " + "F-Measure :" + eval.fMeasure(1) + " Precision :" + eval.precision(1) + " Recall :" + eval.recall(1));

		
		/*To print the confusion Matrix*/
		double[][] dConfusionMatrix = eval.confusionMatrix();
		System.out.println("=== Confusion Matrix ===");
		System.out.println("a"+"\t"+"b");
		for (int i = 0; i < 2; i++) {
			
			for (int j = 0; j < 2; j++) {
				System.out.print((int)dConfusionMatrix[i][j] + "\t");
			}
			if(i==0) System.out.print(" |a =  <=50K");
			if(i==1) System.out.print(" |b =  >50K");
			System.out.println();			
		}

	}

}
