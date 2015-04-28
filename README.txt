
Team Members:
Monisha Singh
Smitha Bangalore Naresh
Utkarsh Jadhav

Submitted Folders & Files : CensusProject, CensusProjectReport.pdf
1)
CensusProject folder contains java files and data to run in eclipse.
The directory structure of CensusProject: 
CensusProject\src:
	CensusProject\src\CensusProject
		CensusProject.java(Main Program)
	CensusProject\src\preprocessing
		ConversionToArff.java
		Preprocessing.java
	CensusProject\src\Ensemble
		MajorityVote.java
CensusProject\lib:
	weka.jar
census-income.data
census-income.test

2)
CensusProjectReport.pdf contains the analysis.

How to compile and run :

1)Open Eclipse. Import CensusProject
2)Copy the lib folder from CensusProject/lib to CensusProject/lib if not copied already and Add weka.jar to build path
If weka.jar is coming as not found follow the steps to add weka.jar to
this project: http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-%28Java%29
Follow Method 1.
3)Copy the data files(census-income.data and census-income.test) into CensusProject folder.
4)Run the project. (NOTE: The time take to get output : 120 secs approx)


Output : 
census-income-data.arff and census-income-test.arff are created from census-income.data
census-income.test and  our project will read data from arff files.

The output of the prediction accuracy is seen on the console as follows:

=== Starting to Run Majority Voting with Replacing Missing Values(Mean & Mode Imputation) ===
=== Number of Training Instances ===
32561

=== Classifier model (full training set) ===

Vote combines the probability distributions of these base learners:
	weka.classifiers.trees.NBTree 
	weka.classifiers.trees.J48 -C 0.1 -M 2
	weka.classifiers.functions.Logistic -R 1.0E-8 -M -1
	weka.classifiers.trees.RandomForest -I 100 -K 0 -S 1
	weka.classifiers.meta.Bagging -P 25 -S 1 -I 10 -W weka.classifiers.trees.J48 -- -C 0.25 -M 2
using the 'Majority Voting' combination rule 

=== Building Model ===
=== Time taken to Build Classifiers & Test ===

85 seconds

Results
===============

Correctly Classified Instances       14075               86.4505 %
Incorrectly Classified Instances      2206               13.5495 %
Kappa statistic                          0.5981
K&B Relative Info Score             937067.8836 %
K&B Information Score                 7391.245  bits      0.454  bits/instance
Class complexity | order 0           12840.958  bits      0.7887 bits/instance
Class complexity | scheme          2369244      bits    145.522  bits/instance
Complexity improvement     (Sf)    -2356403.042 bits   -144.7333 bits/instance
Mean absolute error                      0.1355
Root mean squared error                  0.3681
Relative absolute error                 37.5475 %
Root relative squared error             86.6594 %
Total Number of Instances            16281     

For <50K F-Measure :0.9138348566518241 Precision :0.8884332042226779 Recall :0.9407318053880177
For >50K F-Measure :0.6830459770114943 Precision :0.7633269107257546 Recall :0.6180447217888716
=== Confusion Matrix ===
a	b
11698	737	 |a =  <=50K
1469	2377	 |b =  >50K







