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
public class LogarithmicSimilarity implements Similarity {

	private HashMap<String, Double> extrema = new HashMap<String, Double>();

	public LogarithmicSimilarity() {
		super();
	}
	
	@Override
	public String getName() {
		return "Logarithmic Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		if(extrema.isEmpty()) {
			System.err.println("LogarithmicSimilarity: compute extrema first!");
			return Double.NaN;
		}
		double sd = toLogScale(ValueParser.parse(term1));
		double td = toLogScale(ValueParser.parse(term2));
		return normalize((Math.abs(sd-td)));
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
			double d = ValueParser.parse( s.getPropertyValue(sourceProperty) );
			if(d > maxS) maxS = d;
			if(d < minS) minS = d;
		}
		extrema.put("maxS", maxS);
		extrema.put("minS", minS);
		double maxT = Double.NEGATIVE_INFINITY, minT = Double.POSITIVE_INFINITY;
		for(Resource t : targets) {
			double d = ValueParser.parse( t.getPropertyValue(targetProperty) );
			if(d > maxT) maxT = d;
			if(d < minT) minT = d;
		}
		extrema.put("maxT", maxT);
		extrema.put("minT", minT);
		System.out.println(extrema.toString());
		
		double maxMax = Math.max(extrema.get("maxS"), extrema.get("maxT"));
		extrema.put("maxMax", maxMax);
		double minMin = Math.min(extrema.get("minS"), extrema.get("minT"));
		extrema.put("minMin", minMin);
		// Log(minMin) = Log(1) = 0
		extrema.put("denom", toLogScale(maxMax));

	}
	
	private double toLogScale(double x) {
		return Math.log10(x - extrema.get("minMin") + 1);
	}

	private double normalize(double value) {
		// incomplete information
		if(Double.isNaN(value))
			return Double.NaN;
		
		double denom = extrema.get("denom");
		
		if(denom == 0.0)
			return 1.0;
		else
			return 1.0 - value / denom;
	}

	/**
	 * Test.
	 * @param args
	 */
	public static void main(String[] args) {
		
		LogarithmicSimilarity ls = new LogarithmicSimilarity();
		ls.extrema.put("minMin", 0.0);
		ls.extrema.put("denom", ls.toLogScale(30001.0));
		
		System.out.println(ls.compute("0", "1"));
		System.out.println(ls.compute("30000", "30001"));
		System.out.println(ls.compute("-2", "200"));
		
	}


}
