package org.aksw.simba.ballad.classifier.weka;

import weka.classifiers.functions.SMO;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class SMOClassifier extends WekaClassifier {

	public SMOClassifier(String trainFile, String testFile) {
		super(trainFile, testFile);
		
		this.cmodel = new SMO();
		
		// set parameter default values
		((SMO) this.cmodel).setC(1E+6);
		((SMO) this.cmodel).setEpsilon(1E-3);
	}
	
	public void setC(double val) {
		((SMO) this.cmodel).setC(val);
	}

	public void setEps(double val) {
		((SMO) this.cmodel).setEpsilon(val);
	}

}
