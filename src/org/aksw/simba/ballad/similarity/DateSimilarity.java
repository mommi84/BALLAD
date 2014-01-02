package org.aksw.simba.ballad.similarity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.PropertyAlignment;
import org.aksw.simba.ballad.model.Resource;
import org.aksw.simba.ballad.util.ValueParser;

public class DateSimilarity implements Similarity {

	private HashMap<String, Double> extrema = new HashMap<String, Double>();

	@Override
	public String getName() {
		return "Date Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		if(extrema.isEmpty()) {
			System.err.println("DateSimilarity: compute extrema first!");
			return Double.NaN;
		}
		
		if(term1.isEmpty() || term2.isEmpty())
			return 0.0;
		
		return normalize(getDaysBetween(term1, term2));
	}

	private long getDaysBetween(String term1, String term2) {
		
		Calendar calendar1 = Calendar.getInstance();
	    Calendar calendar2 = Calendar.getInstance();
	    int year1 = Integer.parseInt(term1.substring(0, 4));
	    int month1 = Integer.parseInt(term1.substring(4, 6));
	    int date1 = Integer.parseInt(term1.substring(6, 8));
	    calendar1.set(year1, month1, date1);
	    int year2 = Integer.parseInt(term2.substring(0, 4));
	    int month2 = Integer.parseInt(term2.substring(4, 6));
	    int date2 = Integer.parseInt(term2.substring(6, 8));
	    calendar2.set(year2, month2, date2);
	    long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
	    // the distance in days
	    return Math.abs(diff / (24 * 60 * 60 * 1000));
	    
	}

	@Override
	public int getType() {
		return Property.TYPE_DATE;
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
	}
	
	private double normalize(double value) {
		// incomplete information
		if(Double.isNaN(value))
			return Double.NaN;
		
		double maxMax = Math.max(extrema.get("maxS"), extrema.get("maxT"));
		double minMin = Math.min(extrema.get("minS"), extrema.get("minT"));
		double denom = getDaysBetween("" + (int) maxMax, "" + (int) minMin);
		
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
		
		DateSimilarity ds = new DateSimilarity();
		ds.extrema.put("minS", 19900101.0);
		ds.extrema.put("maxS", 20140101.0);
		ds.extrema.put("minT", 19910101.0);
		ds.extrema.put("maxT", 20130101.0);
		
		System.out.println(ds.compute("19960101", "19951229"));
		
	}


}
