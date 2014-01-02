package org.aksw.simba.ballad.controller;

import java.util.ArrayList;

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
	
	/**
	 * Train all classifiers.
	 * @param oh
	 */
	public static void trainAll(OutputHandler oh) {
		
		String trainFile = oh.getTrainFile();
		String testFile = oh.getTestFile();
		
		train(new LinearSvmClassifier(trainFile, testFile));
		train(new PolySvmClassifier(trainFile, testFile));
		train(new MultilayerPerceptronClassifier(trainFile, testFile));
		train(new RegressionClassifier(trainFile, testFile));
		train(new DecisionTableClassifier(trainFile, testFile));
		train(new NaiveBayesClassifier(trainFile, testFile));
		train(new RandomTreeClassifier(trainFile, testFile));
		train(new LogisticClassifier(trainFile, testFile));
		
		System.out.println("\n========== ACCURACY ===========");
		for(Double d : fscores)
			System.out.print(d + "\t");
		
	}

	public static void train(BasicClassifier bc) {
		System.out.println(bc.getDescription() + "\n" + bc.getDetails());
		fscores.add(bc.getFscore());
	}
	
}
