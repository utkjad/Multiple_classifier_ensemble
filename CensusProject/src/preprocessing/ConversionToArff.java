package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ConversionToArff {

	public void convertToArffFile(String ipfilename, String opfilename) {

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			br = new BufferedReader(new FileReader(ipfilename));
			bw = new BufferedWriter(new FileWriter(opfilename));

			bw.write("@relation census-income-data\r\n");

			/* Writing Attributes */
			bw.write("@attribute age numeric\r\n");
			bw.write("@attribute workclass {'Private','Local-gov','Self-emp-not-inc','Federal-gov','State-gov',"
					+ "'Self-emp-inc','Without-pay','Never-worked'}\r\n");
			bw.write("@attribute fnlwgt numeric\r\n");
			bw.write("@attribute education {'11th','HS-grad','Assoc-acdm','Some-college','10th','Prof-school',"
					+ "'7th-8th','Bachelors','Masters','Doctorate','5th-6th','Assoc-voc','9th','12th',"
					+ "'1st-4th','Preschool'}\r\n");
			bw.write("@attribute education-num numeric\r\n");
			bw.write("@attribute marital-status {'Never-married','Married-civ-spouse','Widowed','Divorced',"
					+ "'Separated','Married-spouse-absent','Married-AF-spouse'}\r\n");
			bw.write("@attribute occupation {'Machine-op-inspct','Farming-fishing','Protective-serv',"
					+ "'Other-service','Prof-specialty','Craft-repair','Adm-clerical','Exec-managerial',"
					+ "'Tech-support','Sales','Priv-house-serv','Transport-moving','Handlers-cleaners',"
					+ "'Armed-Forces'}\r\n");
			bw.write("@attribute relationship {'Own-child','Husband','Not-in-family','Unmarried','Wife',"
					+ "'Other-relative'}\r\n");
			bw.write("@attribute race {'Black','White','Asian-Pac-Islander','Other','Amer-Indian-Eskimo'}\r\n");
			bw.write("@attribute sex {'Male','Female'}\r\n");
			bw.write("@attribute capital-gain numeric\r\n");
			bw.write("@attribute capital-loss numeric\r\n");
			bw.write("@attribute hours-per-week numeric\r\n");
			bw.write("@attribute native-country {'United-States','Cuba','Jamaica','India','Mexico','South',"
					+ "'Puerto-Rico','Honduras','England','Canada','Germany',"
					+ "'Iran','Philippines','Italy','Poland','Columbia','Cambodia','Thailand','Ecuador',"
					+ "'Laos','Taiwan','Haiti','Portugal','Dominican-Republic','El-Salvador','France',"
					+ "'Guatemala','China','Japan','Yugoslavia','Peru','Outlying-US(Guam-USVI-etc)','Scotland',"
					+ "'Trinadad&Tobago','Greece','Nicaragua','Vietnam','Hong','Ireland','Hungary',"
					+ "'Holand-Netherlands'}\r\n");
			bw.write("@attribute Outcome {'<=50K','>50K'}\r\n");

			/* Writing Data */
			bw.write("@data\r\n");
			String line = "";
			while ((line = br.readLine()) != null) {
				bw.write(line + "\r\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (bw != null)
					bw.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}
}