package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.trees.RandomTree;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class RandomTreeClassifier extends WekaClassifier {

	public RandomTreeClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cmodel = new RandomTree();

	}

}
