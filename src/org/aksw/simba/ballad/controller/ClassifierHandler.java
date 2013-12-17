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
	 * The list of classifiers to evaluate.
	 */
	private static ArrayList<BasicClassifier> cs = new ArrayList<>();
	
	/**
	 * Train all classifiers.
	 * @param oh
	 */
	public static void trainAll(OutputHandler oh) {
		
		String trainFile = oh.getTrainFile();
		String testFile = oh.getTestFile();
		
		cs.add(new LinearSvmClassifier(trainFile, testFile));
		cs.add(new PolySvmClassifier(trainFile, testFile));
		cs.add(new MultilayerPerceptronClassifier(trainFile, testFile));
		cs.add(new RegressionClassifier(trainFile, testFile));
		cs.add(new DecisionTableClassifier(trainFile, testFile));
		cs.add(new NaiveBayesClassifier(trainFile, testFile));
		cs.add(new RandomTreeClassifier(trainFile, testFile));
		cs.add(new LogisticClassifier(trainFile, testFile));
		
		for(BasicClassifier c : cs) {
			c.run();
			System.out.println(c.getDescription());
			c.printDetails();
		}
		
		System.out.println("\n========== ACCURACY ===========");
		for(BasicClassifier c : cs) {
			System.out.print(c.getFscore() + "\t");
		}
		
		
	}
	
}
