/*
This class implements the main method where we 
run the Majority Voting algorithm using preprocessing.
 */
package CensusProject;

import java.io.BufferedReader;
import java.util.Scanner;

import preprocessing.*;
import Ensemble.*;
import weka.core.Instances;


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

		Instances train = objPreprocess.readArffFile(dataArfffile);
		Instances test = objPreprocess.readArffFile(testArfffile);
		
		System.out.println("Run Options:");
		System.out.println("1) Enter 1 to run Majority Voting with Replacing Missing Values(Mean & Mode Imputation)");
		System.out.println("2) Enter 2 to run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE");
		System.out.println("3) Enter 3 to run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE then Normalisation");
		System.out.println("4) Enter 4 to run Majority Voting with Removing Missing Values");
		System.out.println("5) Enter 5 to run Majority Voting without any Preprocessing");
		System.out.println("Enter your choice: ");
		
		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();

		if (train != null && test != null) {

			/*
			 * Preprocessing different ways and Running Majority Vote for
			 * Prediction
			 */
			MajorityVote mV = new MajorityVote();
			
			if (choice==1)/*This is our algorithm*/ {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) ===");
				/* Replace Missing Values in Data */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				mV.MajorityVotePrediction(trainReplaceMissingValues, testFilledMissingValues);
			} else if (choice==2) {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE ===");
				/* Replace Missing Values in Data */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Balance The Data using SMOTE */
				Instances trainBalancedData = objPreprocess.BalanceDataSMOTE(trainReplaceMissingValues,65, 5);
				/* Randomize the Data after using SMOTE */
				Instances trainBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(trainBalancedData);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(trainBalancedRandomizedData, testFilledMissingValues);
			} else if (choice==3) {
				System.out.println("=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) then SMOTE then Normalisation ===");
				/* Replace Missing Values in Train */
				Instances trainReplaceMissingValues = objPreprocess.replaceMissingValues(train);
				/* Balance The Data using SMOTE */
				Instances trainBalancedData = objPreprocess.BalanceDataSMOTE(trainReplaceMissingValues, 65, 5);
				/* Randomize the Data after using SMOTE */
				Instances trainBalancedRandomizedData = objPreprocess.AfterSmoteDataRandomize(trainBalancedData);
				/* Normalize the Train Data */
				Instances normalizedTrainData = objPreprocess.NormalizedData(trainBalancedRandomizedData);
				/* Replace Missing Values in Test */
				Instances testFilledMissingValues = objPreprocess.replaceMissingValues(test);
				/* Normalize the Test Data */
				Instances normalizedTestData = objPreprocess.NormalizedData(testFilledMissingValues);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(normalizedTrainData, normalizedTestData);
			} else if (choice==4) {
				System.out.println("=== Starting to Run Majority Voting with Removing Missing Values ===");
				/* Removing Missing Values From Train */
				Instances trainRemoveMissingValues = objPreprocess.removeMissingInstances(train);
				/* Removing Missing Values From Test */
				Instances testRemoveMissingValues = objPreprocess.removeMissingInstances(test);
				/* Running Majority Voting */
				mV.MajorityVotePrediction(trainRemoveMissingValues, testRemoveMissingValues);
			} else if(choice==5){
				System.out.println("=== Starting to Run Majority Voting without any Preprocessing ===");
				mV.MajorityVotePrediction(train, test);
			}
			else{
				System.out.println("Invalid Choice, run the project again");
				System.exit(0);
				}
		}
	}
}
