
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

Output: The output is generated on console. The output.txt file contains the output for various options selected during runtime.