package org.aksw.simba.ballad.similarity;

import java.util.HashMap;

import org.aksw.simba.ballad.model.Property;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class CosineSimilarity implements Similarity {

	@Override
	public String getName() {
		return "Cosine Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity cs = 
		new uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity();
		return new Double(cs.getSimilarity(term1, term2));
	}

	@Override
	public int getType() {
		return Property.TYPE_STRING;
	}

	@Override
	public HashMap<String, Double> getWeights() {
		// weights not supported
		return null;
	}

}
