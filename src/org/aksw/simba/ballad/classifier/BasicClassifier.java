package org.aksw.simba.ballad.classifier;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface BasicClassifier {

	public void run();
	
	public String getDescription();
	
	/**
	 * Compute the f-score of the classification.
	 * @return f-score in percentage
	 */
	public double computeFscore();

	public double getPrecision();
	
	public double getRecall();
	
	public double getFscore();
	
	public void printDetails();

}
