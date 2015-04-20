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
		 * brTestFile.close() ;
		 */

		Instances train = objPreprocess.readArffFile(dataArfffile);
		Instances test = objPreprocess.readArffFile(testArfffile);

		//String selection = "removeMissingValuesRunMAJ";
		String selection = " ";

		if (train != null && test != null) {

			/*
			 * Preprocessing different ways and Running Majority Vote for
			 * Prediction
			 */
			MajorityVote mV = new MajorityVote();
			
			if (selection.equals("replaceMissingValuesRunMAJ")) {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) ===");
				/* Replace Missing Values in Data */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				mV.MajorityVotePrediction(trainReplaceMissingValues, testFilledMissingValues);
			} else if (selection.equals("replaceMissingValuesSMOTERunMAJ")) {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE ===");
				/* Replace Missing Values in Data */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Balance The Data using SMOTE */
				Instances trainBalancedData = objPreprocess.BalanceDataSMOTE(trainReplaceMissingValues, 130, 5);
				/* Randomise the Data after using SMOTE */
				Instances trainBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(trainBalancedData);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(trainBalancedRandomizedData, testFilledMissingValues);
			} else if (selection.equals("replaceMissingValuesSMOTENormaliseRunMAJ")) {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE then Normalisation ===");
				/* Replace Missing Values in Train */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Balance The Data using SMOTE */
				Instances trainBalancedData = objPreprocess.BalanceDataSMOTE(trainReplaceMissingValues, 130, 5);
				/* Randomize the Data after using SMOTE */
				Instances trainBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(trainBalancedData);
				/* Normalize the Train Data */
				Instances normalizedTrainData = objPreprocess.NormalizedData(trainBalancedRandomizedData);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				/* Normalize the Test Data */
				Instances normalizedTestData = objPreprocess.cleanTest(testFilledMissingValues);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(normalizedTrainData, normalizedTestData);
			} else if (selection.equals("removeMissingValuesRunMAJ")) {
				System.out.println("=== Starting to Run Majority Voting with Removing Missing Values ===");
				/* Removing Missing Values From Train */
				Instances trainRemoveMissingValues = objPreprocess.removeMissingInstances(train);
				/* Removing Missing Values From Test */
				Instances testRemoveMissingValues = objPreprocess.removeMissingInstances(test);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(trainRemoveMissingValues, testRemoveMissingValues);
			} else {
				System.out.println("=== Starting to Run Majority Voting without any Preprocessing ===");
				mV.MajorityVotePrediction(train, test);
			}
		}
	}
}
