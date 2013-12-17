package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.functions.MultilayerPerceptron;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class MultilayerPerceptronClassifier extends WekaClassifier {

	public MultilayerPerceptronClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);

		this.cModel = new MultilayerPerceptron();
		run();

	}

}
