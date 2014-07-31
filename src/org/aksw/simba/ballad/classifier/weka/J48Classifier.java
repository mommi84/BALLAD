package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.trees.J48;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class J48Classifier extends WekaClassifier {

	public J48Classifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cmodel = new J48();

	}

}
