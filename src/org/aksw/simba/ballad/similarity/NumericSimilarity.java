package org.aksw.simba.ballad.similarity;

import java.util.HashMap;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.PropertyAlignment;
import org.aksw.simba.ballad.model.Resource;
import org.aksw.simba.ballad.util.ValueParser;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class NumericSimilarity implements Similarity {

	private HashMap<String, Double> extrema = new HashMap<String, Double>();

	public NumericSimilarity() {
		super();
	}
	
	@Override
	public String getName() {
		return "Numeric Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		if(extrema.isEmpty()) {
			System.err.println("NumericSimilarity: compute extrema first!");
			return Double.NaN;
		}
		double sd = Math.log10(ValueParser.parse(term1));
		double td = Math.log10(ValueParser.parse(term2));
		return Math.pow(normalize(Math.abs(sd-td)), 3);
	}

	@Override
	public int getType() {
		return Property.TYPE_NUM;
	}

	@Override
	public HashMap<String, Double> getWeights() {
		// weights not supported
		return null;
	}
	
	public void computeExtrema(Join join, PropertyAlignment propertyAlignment) {
		
		// TODO the current approach ignores one-to-many alignments
		Property sourceProperty = propertyAlignment.getSourceProperties().get(0);
		Property targetProperty = propertyAlignment.getTargetProperties().get(0);

		TreeSet<Resource> sources = join.getSource().getResources();
		TreeSet<Resource> targets = join.getTarget().getResources();
		
		extrema.clear();
		double maxS = Double.NEGATIVE_INFINITY, minS = Double.POSITIVE_INFINITY;
		for(Resource s : sources) {
			double d = Math.log10(ValueParser.parse( s.getPropertyValue(sourceProperty) ));
			if(d > maxS) maxS = d;
			if(d < minS) minS = d;
		}
		extrema.put("maxS", maxS);
		extrema.put("minS", minS);
		double maxT = Double.NEGATIVE_INFINITY, minT = Double.POSITIVE_INFINITY;
		for(Resource t : targets) {
			double d = Math.log10(ValueParser.parse( t.getPropertyValue(targetProperty) ));
			if(d > maxT) maxT = d;
			if(d < minT) minT = d;
		}
		extrema.put("maxT", maxT);
		extrema.put("minT", minT);
		System.out.println(extrema.toString());
	}

	private double normalize(double value) {
		// incomplete information means similarity = 0
		if(Double.isNaN(value))
			return Double.NaN;
		double denom = Math.max(extrema.get("maxT") - extrema.get("minS"), 
				extrema.get("maxS") - extrema.get("minT"));
		if(denom == 0.0)
			return 1.0;
		else
			return 1.0 - value / denom;
	}


}
