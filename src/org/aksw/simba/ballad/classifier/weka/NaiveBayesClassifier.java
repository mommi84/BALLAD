package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.bayes.NaiveBayes;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class NaiveBayesClassifier extends WekaClassifier {

	public NaiveBayesClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);

		this.cModel = new NaiveBayes();
		run();

	}

}
