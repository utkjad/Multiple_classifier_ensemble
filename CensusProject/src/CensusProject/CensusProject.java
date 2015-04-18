/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CensusProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import preprocessing.*;
import Ensemble.*;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.supervised.instance.SMOTE;

/**
 *
 * @author Monisha
 */
public class CensusProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //BufferedReader br = new BufferedReader(new FileReader("census-income-data.arff"));
        
        //Instances train = new Instances(br);
        //train.setClassIndex(train.numAttributes()-1);
        
        //br.close();
        Preprocessing objPreprocess = new Preprocessing();
        BufferedReader br = new BufferedReader(new FileReader("census-income-data.arff"));        
        Instances train = new Instances(br);
        train.setClassIndex(train.numAttributes()-1);
        
        br.close();
        
        BufferedReader br1 = new BufferedReader(new FileReader("census-income-test.arff"));        
        Instances test = new Instances(br1);
        test.setClassIndex(test.numAttributes()-1);
        
        br1.close();
        
        Instances tempReplaceMissingValues = objPreprocess.replaceMissingValues(train);
        Instances tempBalancedData = objPreprocess.BalanceDataSMOTE(tempReplaceMissingValues);
        Instances tempBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(tempBalancedData);
       // Instances normalizedTrainData = objPreprocess.NormalizedData(tempBalancedRandomizedData);
        Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
        //Instances normalizedTestData = objPreprocess.cleanTest(testFilledMissingValues);
        MajorityVote mV = new MajorityVote();
        mV.MajorityVotePrediction(train, test);
        NaiveBayes nB = new NaiveBayes();
        nB.buildClassifier(tempBalancedRandomizedData);
       // Evaluation eval = new Evaluation(tempBalancedRandomizedData);
        //eval.evaluateModel(nB, normalizedTestData);
        
       // System.out.println(eval.toSummaryString("\nResults\n===============\n", true));
        //System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+ eval.recall(1));
    }
    
}
