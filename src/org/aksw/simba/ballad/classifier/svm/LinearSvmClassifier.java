package org.aksw.simba.ballad.classifier.svm;

import libsvm.svm_parameter;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class LinearSvmClassifier extends SvmClassifier {

	public LinearSvmClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		kernelType = svm_parameter.LINEAR;
		run();
	}

	@Override
	public String getDescription() {
		return "Linear Support Vector Machines (LibSVM)";
	}

}
