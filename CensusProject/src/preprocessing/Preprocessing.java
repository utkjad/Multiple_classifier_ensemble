/*
This class is used for Preprocessing given data(both train & test).
It includes methods for Replace missing values with mean & mode,
Using SMOTE, Removing missing values, reading ARFF file etc
 */
package preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.attribute.Normalize;


/**
 *
 * @author Monisha, Smitha, Utkarsh
 */
public class Preprocessing {

	/* Function to Read the Arff file to Instances */
	public Instances readArffFile(String filename) {
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
				if (br != null)
					br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return readInstances;
	}

	/*Replaces missing values using mean and mode imputation method for given Instances
	 *Returns the new Instances mean and mode values replaced for missing value instance*/
	public Instances replaceMissingValues(Instances data) throws Exception {
		ReplaceMissingValues rmv = new ReplaceMissingValues();
		rmv.setInputFormat(data);
		for (int n = 0; n < data.numInstances(); n++) {
			rmv.input(data.instance(n));
		}
		rmv.batchFinished();
		Instances newData = new Instances(data, data.numInstances());
		Instance processed;
		while ((processed = rmv.output()) != null) {
			newData.add(processed);
		}
		return newData;
	}

	/*Use SMOTE with given percentage and kValue
	 *Returns the new set of Instances after applying SMOTE*/
	public Instances BalanceDataSMOTE(Instances newData, double percentage, int kValue) throws Exception {
		SMOTE objSmote = new SMOTE();
		objSmote.setInputFormat(newData);
		objSmote.setPercentage(percentage);
		objSmote.setNearestNeighbors(kValue);
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

	/*Function Randomizes the given Instances. Done after SMOTE
	 *Returns the randomized Instances*/
	public Instances AfterSmoteDataRandomize(Instances data) throws Exception {
		Randomize objRandom = new Randomize();
		objRandom.setRandomSeed(42);
		objRandom.setInputFormat(data);
		for (int n = 0; n < data.numInstances(); n++) {
			objRandom.input(data.instance(n));
		}
		objRandom.batchFinished();
		Instances newData1 = new Instances(data, data.numInstances());
		Instance processed;
		while ((processed = objRandom.output()) != null) {
			newData1.add(processed);
		}
		return newData1;
	}

	/*Function normalizes numeric attributes in the given Instances.
	 *Returns the normalized Instances */
	public Instances NormalizedData(Instances data) throws Exception {
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

	/*Function removes missing value instances from given Instances*/
	public Instances removeMissingInstances(Instances data) {
		/*boolean	hasMissingValue()
		Tests whether an instance has a missing value.*/

		Instances newData = new Instances(data, data.numInstances());
		for (int n = 0; n < data.numInstances(); n++) {
			Instance tempInstance = data.instance(n);
			if(!tempInstance.hasMissingValue()){
				newData.add(tempInstance);
			}			
		}
		return newData;
	}
}
