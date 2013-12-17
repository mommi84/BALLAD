package org.aksw.simba.ballad.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
 
/**
 * TODO Delete me.
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class WekaPlayground {

	public static void main(String[] args) throws Exception {

//		// Declare two numeric attributes
//		Attribute Attribute1 = new Attribute("firstNumeric");
//		Attribute Attribute2 = new Attribute("secondNumeric");
//		
//		// Declare a nominal attribute along with its values
//		FastVector fvNominalVal = new FastVector(3);
//		fvNominalVal.addElement("blue");
//		fvNominalVal.addElement("gray");
//		fvNominalVal.addElement("black");
//		Attribute Attribute3 = new Attribute("aNominal", fvNominalVal);
//		
//		// Declare the class attribute along with its values
//		FastVector fvClassVal = new FastVector(2);
//		fvClassVal.addElement("positive");
//		fvClassVal.addElement("negative");
//		Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
//		
//		// Declare the feature vector
//		FastVector fvWekaAttributes = new FastVector(4);
//		fvWekaAttributes.addElement(Attribute1);   
//		fvWekaAttributes.addElement(Attribute2);   
//		fvWekaAttributes.addElement(Attribute3);   
//		fvWekaAttributes.addElement(ClassAttribute);
//		
//		// Create an empty training set
//		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);      
//		// Set class index
//		isTrainingSet.setClassIndex(3);
//		 
//		// Create the instance
//		Instance iExample = new Instance(4);
//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 1.0);     
//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);     
//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "gray");
//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");
//		 
//		// add the instance
//		isTrainingSet.add(iExample);
		
		DataSource trainds = new DataSource("etc/train.csv");
		Instances train = trainds.getDataSet();
		train.setClassIndex(train.numAttributes()-1);
		
		DataSource testds = new DataSource("etc/test.csv");
		Instances test = testds.getDataSet();
		test.setClassIndex(test.numAttributes()-1);
		
		Classifier cModel = new MultilayerPerceptron();
		cModel.buildClassifier(train);
		
		// Test the model
		Evaluation eTest = new Evaluation(train);
		eTest.evaluateModel(cModel, test);
		
		// Print the result à la Weka explorer:
		String strSummary = eTest.toSummaryString();
		System.out.println(strSummary);
	}
}