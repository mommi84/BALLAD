package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.functions.Logistic;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class LogisticClassifier extends WekaClassifier {

	public LogisticClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);

		this.cModel = new Logistic();
		run();

	}

}
