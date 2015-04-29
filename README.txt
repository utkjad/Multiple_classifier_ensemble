
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
3)Copy the data files(census-income.data and census-income.test) into CensusProject folder.(NOTE: By default already present)
4)Run the project (i.e.CensusProject.java (our main program)). (NOTE: The time take to get output : 100~180 secs approx)
5)You will asked to key in the choice for Run Options on console. Please enter number 2.(Our algorithm)
(NOTE: Output after running other options discussed in report. Same can be tested in our program by choosing respective options)

Output: The output is generated on console. 
NOTE: census-income-data.arff and census-income-test.arff are created from census-income.data
census-income.test and  our project will read data from arff files.

Just For Your Reference:
The output.txt file contains the output for various options selected during runtime.