package org.aksw.simba.ballad.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.Resource;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class WeightedNgramSimilarity implements Similarity {

	private int n;
	private HashMap<String, Double> weights = new HashMap<String, Double>();
	private Property p;
	
	public WeightedNgramSimilarity(Property p, int n) {
		super();
		this.p = p;
		this.n = n;
	}
	
	@Override
	public String getName() {
		return "Weighted N-Gram Similarity";
	}

	@Override
	public Double compute(String term1, String term2) {
		ArrayList<String> ng1 = getNgrams(term1, n);
		ArrayList<String> ng2 = getNgrams(term2, n);
		ArrayList<String> ngint = intersect(ng1, ng2);
		double w1 = 0.0, w2 = 0.0, wint = 0.0;
		for(String ng : ng1)
			w1 += getWeight(ng);
		for(String ng : ng2)
			w2 += getWeight(ng);
		for(String ng : ngint)
			wint += getWeight(ng);
		if(w1+w2 == 0)
			return 1.0;
		return 2 * wint / (w1 + w2);
	}

	private ArrayList<String> intersect(ArrayList<String> set1, ArrayList<String> set2) {
		ArrayList<String> intset = new ArrayList<String>(set1);
		ArrayList<String> temp = new ArrayList<String>();
		for(String s : set2)
			temp.add(s);
	    Iterator<String> e = intset.iterator();
	    while (e.hasNext()) {
	    	String item = e.next();
	        if (!temp.contains(item))
		        e.remove();
	        else
	        	temp.remove(item);
	    }
	    return intset;
	}

	private ArrayList<String> getNgrams(String s, int n) {
		s = s.toLowerCase();
		ArrayList<String> ngrams = new ArrayList<String>();
		for(int i=0; i<n-1; i++)
			s = "-" + s + "-";
		for(int i=0; i<=s.length()-n; i++)
			ngrams.add(s.substring(i, i+n));
		return ngrams;
	}

	private double getWeight(String ng) {
		Double d = weights.get(ng.toLowerCase());
		if(d == null)
			return 1.0;
		else
			return d;
	}
	@Override
	public int getType() {
		return Property.TYPE_STRING;
	}

	@Override
	public HashMap<String, Double> getWeights() {
		return weights;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void loadWeightsFromDatasets(Join join) {
		ArrayList<Resource> sources = new ArrayList<Resource>(join.getSource().getResources());
		ArrayList<Resource> targets = new ArrayList<Resource>(join.getTarget().getResources());
		
		HashMap<String, Integer> tf_gen = new HashMap<String, Integer>();
		HashMap<String, Integer> idf_den = new HashMap<String, Integer>();
		
		buildTfIdf(sources, tf_gen, idf_den, p);
		// TODO one-to-many approach
		buildTfIdf(targets, tf_gen, idf_den, p.getAlignment(0));
		
		for(String ng : idf_den.keySet()) {
			double tf = (double) tf_gen.get(ng);
			double idf = Math.log((double) (sources.size()+targets.size()) / (double) idf_den.get(ng));
			weights.put(ng, tf * idf);
		}
		
		double max = 0.0;
		for(Double d : weights.values())
			if(d > max)
				max = d;
		for(String k : weights.keySet())
			weights.put(k, weights.get(k) / max);
		
		System.out.println(weights);
	}
	
	private void buildTfIdf(ArrayList<Resource> resources, HashMap<String, Integer> tf_gen, HashMap<String, Integer> idf_den,
			Property property) {
		for(Resource r : resources) {
			HashMap<String, Integer> tf_p = new HashMap<String, Integer>();
			ArrayList<String> ngs = getNgrams(r.getPropertyValue(property), n);
			for(String ng : ngs) {
				Integer cnt = tf_p.get(ng);
				if(cnt == null)
					tf_p.put(ng, 1);
				else
					tf_p.put(ng, cnt+1);
			}
			for(String ng : tf_p.keySet()) {
				Integer part = tf_gen.get(ng);
				if(part == null)
					tf_gen.put(ng, tf_p.get(ng));
				else
					tf_gen.put(ng, part + tf_p.get(ng));
				
				Integer cnt = idf_den.get(ng);
				if(cnt == null)
					idf_den.put(ng, 1);
				else
					idf_den.put(ng, cnt+1);
			}
		}
	}

}
