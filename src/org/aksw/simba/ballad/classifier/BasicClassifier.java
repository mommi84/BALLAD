package org.aksw.simba.ballad.classifier;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface BasicClassifier {

	public void run();
	
	public String getDescription();
	
	public double getPrecision();
	
	public double getRecall();
	
	public double getFscore();
	
	public String getDetails();

}
