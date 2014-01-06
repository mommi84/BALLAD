package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.rules.DecisionTable;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class DecisionTableClassifier extends WekaClassifier {

	public DecisionTableClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cmodel = new DecisionTable();

	}

}
