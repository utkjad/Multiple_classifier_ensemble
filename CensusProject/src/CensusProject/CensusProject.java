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
 * @author Monisha, Smitha, Utkarsh
 */
public class CensusProject {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {

		/* Conversion of Given Files to Arff files */
		String dataArfffile = "census-income-data.arff";
		String testArfffile = "census-income-test.arff";

		ConversionToArff objConversionToArff = new ConversionToArff();
		objConversionToArff.convertToArffFile("census-income.data", dataArfffile);
		objConversionToArff.convertToArffFile("census-income.test", testArfffile);

		/* Now Read the Arff files to Instances */
		Preprocessing objPreprocess = new Preprocessing();
		/*
		 * BufferedReader brDataFile = new BufferedReader(new
		 * FileReader(dataArfffile)); Instances train = new
		 * Instances(brDataFile); train.setClassIndex(train.numAttributes() -
		 * 1);
		 * 
		 * brDataFile.close();
		 * 
		 * BufferedReader brTestFile = new BufferedReader(new
		 * FileReader(testArfffile)); Instances test = new
		 * Instances(brTestFile); test.setClassIndex(test.numAttributes() - 1);
		 * 
		 * brTestFile.close()
		 ;*/

		Instances train = objPreprocess.readArffFile(dataArfffile);
		Instances test = objPreprocess.readArffFile(testArfffile);

		if (train != null && test != null) {
			/* Preprocessing */

			Instances tempReplaceMissingValues = objPreprocess.replaceMissingValues(train);
			Instances tempBalancedData = objPreprocess.BalanceDataSMOTE(tempReplaceMissingValues);
			Instances tempBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(tempBalancedData);
			// Instances normalizedTrainData =
			// objPreprocess.NormalizedData(tempBalancedRandomizedData);
			Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
			// Instances normalizedTestData =
			// objPreprocess.cleanTest(testFilledMissingValues);

			/* Use Majority Vote for Prediction */
			MajorityVote mV = new MajorityVote();
			mV.MajorityVotePrediction(train, test);
		}
	}

}
