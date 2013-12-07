package org.aksw.simba.ballad.similarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.util.Transform;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class WeightedEditSimilarity implements Similarity {
	
	public static final double INIT_FULL_WEIGHT = 1.0;
	public static final double INIT_CASE_WEIGHT = 0.5;
	private HashMap<String, Double> weights = new HashMap<String, Double>();

	public WeightedEditSimilarity() {
		super();
	}
	
	@Override
	public String getName() {
		return "Weighted Edit Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		return Transform.toSimilarity(wed.proximity(term1, term2));
	}

	@Override
	public int getType() {
		return Property.TYPE_STRING;
	}

	private WeightedEditDistanceExtended wed = new WeightedEditDistanceExtended() {
		@Override
		public double transposeWeight(char cFirst, char cSecond) {
			return Double.POSITIVE_INFINITY;
		}
		@Override
		public double substituteWeight(char cDeleted, char cInserted) {
			Double d = weights.get(cDeleted+","+cInserted);
			if(d == null)
				return INIT_FULL_WEIGHT;
			else
				return d;
		}
		@Override
		public double matchWeight(char cMatched) {
			return 0.0;
		}
		@Override
		public double insertWeight(char cInserted) {
			Double d = weights.get("&,"+cInserted);
			if(d == null)
				return INIT_FULL_WEIGHT;
			else
				return d;
		}
		@Override
		public double deleteWeight(char cDeleted) {
			Double d = weights.get(cDeleted+",&");
			if(d == null)
				return INIT_FULL_WEIGHT;
			else
				return d;
		}
	};

	/**
	 * Here, `&` is used instead of `epsilon`, meaning the empty string.
	 */
	public void loadWeightsFromFile(String filePath) {
		
		// load case-transformation weights
		for(char c='A'; c<='Z'; c++) {
			weights.put(c+","+(char)(c+32), INIT_CASE_WEIGHT);
			weights.put((char)(c+32)+","+c, INIT_CASE_WEIGHT);
		}
		
		// load confusion matrix
		Scanner in = null;
		try {
			in = new Scanner(new File(filePath)); // "etc/confusion-matrix.txt"
		} catch (FileNotFoundException e) {
			System.err.println("Missing file `./" + filePath + "`!");
			return;
		}
		for(char c1='a'; c1<='{'; c1++) {
			for(char c2='a'; c2<='{'; c2++) {
				double d = in.nextDouble();
				if(d != 1) {
					if(c1 == '{') {
						weights.put("&,"+c2, d);
						weights.put("&,"+(char)(c2-32), d);
					} else 	if(c2 == '{') {
						weights.put(c1+",&", d);
						weights.put((char)(c1-32)+",&", d);
					} else {
						weights.put(c1+","+c2, d);
						weights.put((char)(c1-32)+","+(char)(c2-32), d);
						// crossing weights (e.g., <A,b>, <a,B>)
						double dcross = (1.0 + d) / 2.0;
						weights.put((char)(c1-32)+","+c2, dcross);
						weights.put(c1+","+(char)(c2-32), dcross);
					}
				}
			}
		}
		in.close();
	}

	@Override
	public HashMap<String, Double> getWeights() {
		return weights;
	}
	
}


