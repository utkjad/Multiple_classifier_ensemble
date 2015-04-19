/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.SimpleFilter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.attribute.Normalize;
import weka.core.converters.CSVLoader.*;

/**
 *
 * @author Monisha, Smitha, Utkarsh
 */
public class Preprocessing {

	/*Function to Read the Arff file to Instances*/
	public static Instances readArffFile(String filename) {
		BufferedReader br = null;
		Instances readInstances = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			readInstances = new Instances(br);
			readInstances.setClassIndex(readInstances.numAttributes() - 1);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (br != null) br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return readInstances;
	}

	public static Instances replaceMissingValues(Instances train) throws Exception {
		ReplaceMissingValues rmv = new ReplaceMissingValues();
		rmv.setInputFormat(train);
		for (int n = 0; n < train.numInstances(); n++) {
			rmv.input(train.instance(n));
		}
		rmv.batchFinished();
		Instances newData = new Instances(train, train.numInstances());
		Instance processed;
		while ((processed = rmv.output()) != null) {
			newData.add(processed);
		}
		return newData;
	}

	public static Instances BalanceDataSMOTE(Instances newData) throws Exception {
		SMOTE objSmote = new SMOTE();
		objSmote.setInputFormat(newData);
		objSmote.setPercentage(100.0);
		objSmote.setNearestNeighbors(5);
		objSmote.setClassValue("0");
		objSmote.setRandomSeed(1);
		for (int n = 0; n < newData.numInstances(); n++) {
			objSmote.input(newData.instance(n));
		}
		objSmote.batchFinished();
		Instances newData1 = new Instances(newData, newData.numInstances());
		Instance processed;
		while ((processed = objSmote.output()) != null) {
			newData1.add(processed);
		}
		return newData1;
	}

	public static Instances AfterSmoteDataRandomize(Instances train) throws Exception {
		Randomize objRandom = new Randomize();
		objRandom.setRandomSeed(42);
		objRandom.setInputFormat(train);
		for (int n = 0; n < train.numInstances(); n++) {
			objRandom.input(train.instance(n));
		}
		objRandom.batchFinished();
		Instances newData1 = new Instances(train, train.numInstances());
		Instance processed;
		while ((processed = objRandom.output()) != null) {
			newData1.add(processed);
		}
		return newData1;
	}

	public static Instances NormalizedData(Instances data) throws Exception {
		Normalize objNormalize = new Normalize();
		objNormalize.setInputFormat(data);
		for (int n = 0; n < data.numInstances(); n++) {
			objNormalize.input(data.instance(n));
		}
		objNormalize.batchFinished();
		Instances newData1 = new Instances(data, data.numInstances());
		Instance processed;
		while ((processed = objNormalize.output()) != null) {
			newData1.add(processed);
		}
		return newData1;
	}

	public static Instances cleanTest(Instances test) throws Exception {
		Instances tempReplaceMissingValuesTest = replaceMissingValues(test);
		// Instances normalizedTestData =
		// NormalizedData(tempReplaceMissingValuesTest);
		// return normalizedTestData;
		return tempReplaceMissingValuesTest;
	}
}
