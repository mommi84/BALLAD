package org.aksw.simba.ballad.classifier.weka;

import org.aksw.simba.ballad.classifier.BasicClassifier;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public abstract class WekaClassifier implements BasicClassifier {
	
	private String trainFile;
	private String testFile;
	private double tp = 0, fp = 0, tn = 0, fn = 0, precision, recall, fscore;
	
	protected Classifier cModel;
	
	private Evaluation eTest;
	private int classIndex;
	private Instances train, test;
	private DataSource trainds, testds;

	
	public WekaClassifier(String trainFile, String testFile) {
		
		this.trainFile = trainFile;
		this.testFile = testFile;

	}
	
	public void run() {

		try {
						
			trainds = new DataSource(trainFile);
			train = removeFirstAttribute( trainds.getDataSet() );
			classIndex = train.numAttributes()-1;
			train.setClassIndex(classIndex);
			
			testds = new DataSource(testFile);
			test = removeFirstAttribute( testds.getDataSet() );
			test.setClassIndex(classIndex);

			cModel.buildClassifier(train);
			
			// Test the model
			eTest = new Evaluation(train);
			eTest.evaluateModel(cModel, test);
			
		} catch (Exception e) {
			
			System.err.println("Error during classification.");
			e.printStackTrace();
			return;
			
		}
		
		// Print the result à la Weka explorer:
		String strSummary = eTest.toSummaryString();
		System.out.println(strSummary);

		// class 1 is "positive"
		tp = eTest.numTruePositives(1);
		tn = eTest.numTrueNegatives(1);
		fp = eTest.numFalsePositives(1);
		fn = eTest.numFalseNegatives(1);
		precision = eTest.precision(1) * 100;
		recall = eTest.recall(1) * 100;
		fscore = eTest.fMeasure(1) * 100;
	
	}
	
	private Instances removeFirstAttribute(Instances inst) {
		
		String[] options = new String[2];
		// "range"
		options[0] = "-R";
		// first attribute
		options[1] = "1";
		// new instance of filter
		Remove remove = new Remove();
		// set options
		try {
			remove.setOptions(options);
			// inform filter about dataset **AFTER** setting options
			remove.setInputFormat(inst);
			// apply filter
			return Filter.useFilter(inst, remove);
		} catch (Exception e) {
			System.err.println("Can't remove first attribute.");
			return null;
		}
		
	}

	public String getTrainFile() {
		return trainFile;
	}

	public void setTrainFile(String trainFile) {
		this.trainFile = trainFile;
	}

	public String getTestFile() {
		return testFile;
	}

	public void setTestFile(String testFile) {
		this.testFile = testFile;
	}

	public String getDescription() {
		return cModel.toString();
	}

	public Classifier getcModel() {
		return cModel;
	}	
	
	public double getPrecision() {
		return precision;
	}

	public double getRecall() {
		return recall;
	}

	public double getFscore() {
		return fscore;
	}

	public void printDetails() {
		
		System.out.println("tp = "+tp+"\tfp = "+fp);
		System.out.println("tn = "+tn+"\tfn = "+fn);
		System.out.println("pr% = "+precision+"\nrc% = "+recall);
		System.out.println("fscore% = "+fscore);

	}
}
