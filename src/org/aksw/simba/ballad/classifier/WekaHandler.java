package org.aksw.simba.ballad.classifier;

import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class WekaHandler {
	
	public static void run(String trainFile, String testFile) throws Exception {
		
		DataSource src = new DataSource(trainFile);
		DataSource tgt = new DataSource(testFile);
		Instances train = src.getDataSet();
		Instances test = tgt.getDataSet();
		
		// train classifier
		Classifier cls = new J48();
		cls.buildClassifier(train);
		
		// evaluate classifier and print some statistics
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(cls, test);
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
	
	public static void main(String[] args) throws Exception {
		run("etc/train.csv", "etc/test.csv");
	}
	
}
