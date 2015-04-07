/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package censusproject.preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.core.converters.ArffSaver;
import weka.filters.SimpleFilter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.attribute.Normalize;


/**
 *
 * @author Monisha
 */
public class Preprocessing{
   
    public static void readArffFile() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("census-income-data.arff"));        
        Instances train = new Instances(br);
        train.setClassIndex(train.numAttributes()-1);
        
        br.close();
}
    public static Instances replaceMissingValues(Instances train) throws Exception{
        ReplaceMissingValues rmv = new ReplaceMissingValues();
        rmv.setInputFormat(train);
        for(int n = 0; n<train.numInstances(); n++){
            rmv.input(train.instance(n));                
        }
        rmv.batchFinished();
        Instances newData = new Instances(train,train.numInstances());
        Instance processed;
         while ((processed = rmv.output()) != null) {
            newData.add(processed);
  }
        return newData;
    }
    public static Instances BalanceDataSMOTE(Instances newData) throws Exception{
        SMOTE objSmote = new SMOTE();
        objSmote.setInputFormat(newData);
        objSmote.setPercentage(100.0);
        objSmote.setNearestNeighbors(5);
        objSmote.setClassValue("0");
        objSmote.setRandomSeed(1);
        for(int n = 0; n<newData.numInstances(); n++){
            objSmote.input(newData.instance(n));                
        }
        objSmote.batchFinished();
        Instances newData1 = new Instances(newData,newData.numInstances());
        Instance processed;
         while ((processed = objSmote.output()) != null) {
            newData1.add(processed);
  }
         return newData1;
    }
    public static Instances AfterSmoteDataRandomize(Instances train) throws Exception{
        Randomize objRandom = new Randomize();
        objRandom.setRandomSeed(42);
        objRandom.setInputFormat(train);
        for(int n = 0; n<train.numInstances(); n++){
            objRandom.input(train.instance(n));                
        }
        objRandom.batchFinished();
        Instances newData1 = new Instances(train,train.numInstances());
        Instance processed;
         while ((processed = objRandom.output()) != null) {
            newData1.add(processed);
  }
         return newData1;
    }
    public static Instances NormalizedData(Instances data) throws Exception{
        Normalize objNormalize = new Normalize();
        objNormalize.setInputFormat(data);
        for(int n = 0; n<data.numInstances(); n++){
            objNormalize.input(data.instance(n));                
        }
        objNormalize.batchFinished();
        Instances newData1 = new Instances(data,data.numInstances());
        Instance processed;
         while ((processed = objNormalize.output()) != null) {
            newData1.add(processed);
  }
         return newData1;
    }
    public static Instances cleanTest(Instances test)throws Exception{
        Instances tempReplaceMissingValuesTest = replaceMissingValues(test);
        //Instances normalizedTestData = NormalizedData(tempReplaceMissingValuesTest);
        //return normalizedTestData;
        return tempReplaceMissingValuesTest;
    }
}
