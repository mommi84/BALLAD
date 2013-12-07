package org.aksw.simba.ballad.similarity;

import java.util.HashMap;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface Similarity {

	public String getName();
	public Double compute(String term1, String term2);
	public int getType();
	public HashMap<String, Double> getWeights();
	
}
