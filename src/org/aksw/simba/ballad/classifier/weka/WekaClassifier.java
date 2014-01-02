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
	
	protected Classifier cmodel;
	
	private Evaluation eTest;
	private int classIndex;
	private Instances train, test;
	private DataSource trainds, testds;
	
	public WekaClassifier(String trainFile, String testFile) {
		
		this.trainFile = trainFile;
		this.testFile = testFile;

	}
	
	public void run() {
		
		boolean isUpsideDown;
		
		try {
			
			trainds = new DataSource(trainFile);
			train = removeFirstAttribute( trainds.getDataSet() );
			testds = new DataSource(testFile);
			test = removeFirstAttribute( testds.getDataSet() );
			
			classIndex = train.numAttributes()-1;
			train.setClassIndex(classIndex);
			test.setClassIndex(classIndex);
			
			String firstTrain = (String) train.attribute(classIndex).enumerateValues().nextElement();
			String firstTest = (String) test.attribute(classIndex).enumerateValues().nextElement();
			
			isUpsideDown = !firstTrain.equals(firstTest);
			
			cmodel.buildClassifier(train);
			
			// Test the model
			eTest = new Evaluation(train);
			eTest.evaluateModel(cmodel, test);
			
		} catch (Exception e) {
			
			System.err.println("Error during classification.");
			e.printStackTrace();
			return;
			
		}
		
		// Print the result à la Weka explorer:
		System.out.println(eTest.toSummaryString());

		// class 1 is "positive"
		int posClass = 1;
		if(!isUpsideDown) {
			tp = eTest.numTruePositives(posClass);
			tn = eTest.numTrueNegatives(posClass);
			fp = eTest.numFalsePositives(posClass);
			fn = eTest.numFalseNegatives(posClass);
			precision = eTest.precision(posClass) * 100;
			recall = eTest.recall(posClass) * 100;
			fscore = eTest.fMeasure(posClass) * 100;
		} else {
			fp = eTest.numTruePositives(posClass);
			fn = eTest.numTrueNegatives(posClass);
			tp = eTest.numFalsePositives(posClass);
			tn = eTest.numFalseNegatives(posClass);
			precision = (tp+fp == 0) ? 0.0 : tp / (tp+fp) * 100.0;
			recall = (tp+fp == 0) ? 0.0 : tp / (tp+fn) * 100.0;
			fscore = (precision+recall == 0) ? 0.0 : 2 * precision * recall / (precision + recall);
		}
	
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
		return cmodel.toString();
	}

	public Classifier getClassifierModel() {
		return cmodel;
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

	public String getDetails() {
		
		String details = "";
		details += "tp = " + tp + "\tfp = " + fp + "\n";
		details += "tn = " + tn + "\tfn = " + fn + "\n";
		details += "pr% = " + precision + "\nrc% = " + recall + "\n";
		details += "fscore% = " + fscore;
		return details;

	}
}
