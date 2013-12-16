package org.aksw.simba.ballad.classifier;

import weka.classifiers.trees.RandomTree;

public class RandomTreeClassifier extends WekaClassifier {

	public RandomTreeClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cModel = new RandomTree();
		run();

	}

}
