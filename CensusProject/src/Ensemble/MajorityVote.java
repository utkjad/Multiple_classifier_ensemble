package Ensemble;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.core.SelectedTag;
public class MajorityVote {
	
	public static void MajorityVotePrediction(Instances train, Instances test) throws Exception{
		
		Vote objVote = new Vote();
		SelectedTag objTag = new SelectedTag(Vote.MAJORITY_VOTING_RULE, Vote.TAGS_RULES);
		
		objVote.setCombinationRule(objTag);
		Classifier objNaiveBayes = new NaiveBayes();
		Classifier objJ48 = new J48();
		Classifier objREPTree = new REPTree();
		Classifier[] objClassifiers= {objNaiveBayes, objJ48, objREPTree} ;
			
		   
		objVote.setClassifiers(objClassifiers);
		objVote.buildClassifier(train);
		
		
        Evaluation eval = new Evaluation(test);
        eval.evaluateModel(objVote, test);
        
        System.out.println(eval.toSummaryString("\nResults\n===============\n", true));
        System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+ eval.recall(1));

	}

}
