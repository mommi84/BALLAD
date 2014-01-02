package org.aksw.simba.ballad.classifier.svm;

import libsvm.svm_parameter;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class PolySvmClassifier extends SvmClassifier {

	public PolySvmClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		kernelType = svm_parameter.POLY;
		this.degree = 3;
		run();
	}

	public PolySvmClassifier(String trainFile, String testFile, int degree) {
		super(trainFile, testFile);
		kernelType = svm_parameter.POLY;
		this.degree = degree;
		run();
	}

	@Override
	public String getDescription() {
		return "Degree-" + degree + " Polynomial Support Vector Machines (LibSVM)";
	}

}
