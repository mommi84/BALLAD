package org.aksw.simba.ballad.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public abstract class WekaClassifier {
	
	private String name;
	private String trainFile;
	private String testFile;
	
	protected Classifier cModel;
	
	private Evaluation eTest;
	private int classIndex;
	private Instances train, test;
	
	public WekaClassifier(String trainFile, String testFile) {
		
		this.trainFile = trainFile;
		this.testFile = testFile;
		
	}
	
	public void run() {

		DataSource trainds, testds;
		
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

	public String getName() {
		return name;
	}

	public Classifier getcModel() {
		return cModel;
	}	
	
	public double getFScore() {
		
		double tp = 0, fp = 0, tn = 0, fn = 0;
		int value, thValue;
		
		for(int j=0; j<test.numInstances(); j++) {
			Instance ins = test.instance(j);
			try {
				value = (int) Math.round( cModel.classifyInstance(ins) );
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			thValue = (int) Math.round( ins.value(classIndex) );
			if(thValue == 1) {
				if(value == 1)
					tp++;
				else
					fn++;
			} else {
				if(value == 0)
					tn++;
				else
					fp++;
			}
		}
		
		double pr = tp / (tp + fp);
		double rc = tp / (tp + fn);
		
		System.out.println("tp = "+tp+"\tfp = "+fp);
		System.out.println("tn = "+tn+"\tfn = "+fn);
		System.out.println("pr = "+pr+"\nrc = "+rc);
		
		return 2 * pr * rc / (pr + rc);
		
	}
}
