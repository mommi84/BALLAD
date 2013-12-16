package org.aksw.simba.ballad.classifier;

import weka.classifiers.functions.LinearRegression;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class RegressionClassifier extends WekaClassifier {

	public RegressionClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);

		this.cModel = new LinearRegression();
		run();

	}

}
