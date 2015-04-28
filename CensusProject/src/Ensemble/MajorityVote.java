package Ensemble;

import java.util.concurrent.TimeUnit;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.Bagging;
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

		/*NBTree Classifier*/
		NBTree objNBTree = new NBTree();
		
		/*J48 -C 0.1 -M 2
		 * -C <pruning confidence> Sets confidence threshold for pruning.(default 0.25)
		 * M <minimum number of instances> Sets minimum number of instances per leaf.
  		 * (default 2) (minimum instances per leaf guarantees that at
  		 *  each split, at least 2 of the branches)*/
		J48 objJ48 = new J48();	
		objJ48.setConfidenceFactor((float) 0.1);
		
		/*Logistic -R 1.0E-8 -M -1 
		 * -R Set the ridge in the log-likelihood.
		 * -M Sets the maximum number of iterations (default -1, until convergence)
		 */
		Logistic objLogistic = new Logistic();
		
		/*Bagging -P 100 -S 1 -I 10 -W weka.classifiers.trees.J48 -- -C 0.25 -M 2
		 * -P sets Size of each bag, as a percentage of the training set size.
		 * -S sets Random number seed.(default 1)
		 * -I sets Number of iterations.(default 10)
		 * -W sets Full name of base classifier
		 * Using with J48 as base classifier*/
		Bagging objBagging = new Bagging();
		
		/*Sets Size of each bag, as a percentage of the training set size.*/
		objBagging.setBagSizePercent(25);
		J48 objBaseClassifer = new J48();
		objBagging.setClassifier(objBaseClassifer);
		
		/* RandomForest -I 100 -K 0 -S 1
		 *  -I <number of trees> Sets Number of trees to build. (default 100)
		 *  -K <number of features> Sets Number of features to consider 
		 *  (<1=int(log_2(#predictors)+1)). default (0)
		 *  -S Sets Seed for random number generator. (default 1)*/
		RandomForest objRandomForest = new RandomForest();

		/*AttributeSelectedClassifier objASCREPTree = new AttributeSelectedClassifier();
		CfsSubsetEval objCfsSubsetEval = new CfsSubsetEval();
		objASCREPTree.setEvaluator(objCfsSubsetEval);
		GreedyStepwise objGreedyStepwiseSearch = new GreedyStepwise();
		objASCREPTree.setSearch(objGreedyStepwiseSearch);
		objASCREPTree.setClassifier(new REPTree());*/
		
		
		/*Classifiers to combine in Majority Voting*/
		Classifier[] objClassifiers = { objNBTree, objJ48, objLogistic, objRandomForest, objBagging };
		
		objVote.setClassifiers(objClassifiers);	
		
		System.out.println("=== Number of Training Instances ===");
		System.out.println(train.numInstances());
		System.out.println();
		
		System.out.println("=== Classifier model (full training set) ===");
		System.out.println();
		System.out.println(objVote);
		
		System.out.println("=== Building Model ===");
		long starttime = System.currentTimeMillis();
		/*Build all the classifiers*/
		objVote.buildClassifier(train);
		
		/*Use majority vote to predict test set*/
		Evaluation eval = new Evaluation(test);
		eval.evaluateModel(objVote, test);
        
		long stoptime = System.currentTimeMillis();
		long elapsedtime = stoptime - starttime;
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedtime);
        
		/*Writing Output*/
	
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
