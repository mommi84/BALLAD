package org.aksw.simba.ballad.controller;

import java.util.ArrayList;

import org.aksw.simba.ballad.Bundle;
import org.aksw.simba.ballad.classifier.BasicClassifier;
import org.aksw.simba.ballad.classifier.svm.LinearSvmClassifier;
import org.aksw.simba.ballad.classifier.svm.PolySvmClassifier;
import org.aksw.simba.ballad.classifier.weka.DecisionTableClassifier;
import org.aksw.simba.ballad.classifier.weka.LogisticClassifier;
import org.aksw.simba.ballad.classifier.weka.MultilayerPerceptronClassifier;
import org.aksw.simba.ballad.classifier.weka.NaiveBayesClassifier;
import org.aksw.simba.ballad.classifier.weka.RandomTreeClassifier;
import org.aksw.simba.ballad.classifier.weka.RegressionClassifier;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class ClassifierHandler {

	/**
	 * The list of f-scores.
	 */
	private static ArrayList<Double> fscores = new ArrayList<Double>();
	private static ArrayList<Double> runtimes = new ArrayList<Double>();
	private static long time;
	
	/**
	 * Train all classifiers.
	 * @param oh
	 */
	public static void trainAll(OutputHandler oh) {
		
		String trainFile = oh.getTrainFile();
		String testFile = oh.getTestFile();
		
		time = System.currentTimeMillis();
		
		train(new LinearSvmClassifier(trainFile, testFile));
		train(new PolySvmClassifier(trainFile, testFile));
		train(new MultilayerPerceptronClassifier(trainFile, testFile));
		train(new RegressionClassifier(trainFile, testFile));
		train(new DecisionTableClassifier(trainFile, testFile));
		train(new NaiveBayesClassifier(trainFile, testFile));
		train(new RandomTreeClassifier(trainFile, testFile));
		train(new LogisticClassifier(trainFile, testFile));
		
		System.out.println("\n========== RUNTIME ===========");
		for(Double d : runtimes)
			System.out.print(d + "\t");
		System.out.println("\n========== F-SCORE ===========");
		for(Double d : fscores)
			System.out.print(d + "\t");
		
	}

	public static void train(BasicClassifier bc) {
		try {
			if(bc instanceof LinearSvmClassifier)
				((LinearSvmClassifier) bc).setC(Double.parseDouble(Bundle.getString("svm_parameter_c")));
			if(bc instanceof PolySvmClassifier)
				((PolySvmClassifier) bc).setC(Double.parseDouble(Bundle.getString("svm_parameter_c")));
		} catch (NumberFormatException e) {
			System.out.println("Key 'svm_parameter_c' not found, using default value.");
		}
		try {
			if(bc instanceof LinearSvmClassifier)
				((LinearSvmClassifier) bc).setEps(Double.parseDouble(Bundle.getString("svm_parameter_eps")));
			if(bc instanceof PolySvmClassifier)
				((PolySvmClassifier) bc).setEps(Double.parseDouble(Bundle.getString("svm_parameter_eps")));
		} catch (NumberFormatException e) {
			System.out.println("Key 'svm_parameter_eps' not found, using default value.");
		}
		
		bc.run();
		
		System.out.println(bc.getDescription() + "\n" + bc.getDetails());
		fscores.add(bc.getFscore());
		
		double runtime = (System.currentTimeMillis() - time) / 1000.0;
		runtimes.add(runtime);
		System.out.println("TIME: "+runtime);
		time = System.currentTimeMillis();
	}
	
}
