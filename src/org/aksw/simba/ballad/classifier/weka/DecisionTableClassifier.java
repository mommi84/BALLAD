package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.rules.DecisionTable;

public class DecisionTableClassifier extends WekaClassifier {

	public DecisionTableClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cModel = new DecisionTable();
		run();

	}

}
