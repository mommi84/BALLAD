package org.aksw.simba.ballad.model;

import java.util.HashMap;

import org.aksw.simba.ballad.util.Convention;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Link implements Comparable<Link> {

	public static final int LABEL_NO = -1;
	public static final int UNLABELLED = 0;
	public static final int LABEL_YES = 1;

	private Resource source;
	private Resource target;
	private HashMap<PropertyAlignment, Double> similarities = new HashMap<PropertyAlignment, Double>();
	private int label = UNLABELLED;

	public Link(Resource source, Resource target) {
		super();
		this.source = source;
		this.target = target;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public Resource getSource() {
		return source;
	}

	public void setSource(Resource source) {
		this.source = source;
	}

	public Resource getTarget() {
		return target;
	}

	public void setTarget(Resource target) {
		this.target = target;
	}

	public Double getSimilarityAt(PropertyAlignment pa) {
		return similarities.get(pa);
	}
	
	public void addSimilarity(PropertyAlignment pa, Double similarity) {
		similarities.put(pa, similarity);
	}

	public String getId() {
		return Convention.toID(source.getId(), target.getId());
	}

	@Override
	public int compareTo(Link o) {
		int f = source.compareTo(o.getSource());
		if(f == 0)
			return target.compareTo(o.getTarget());
		return f;
	}
}
