package org.aksw.simba.ballad.model;

import java.util.ArrayList;

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
	private ArrayList<Double> similarities = new ArrayList<>();
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

	public Double getSimilarity(int i) {
		return similarities.get(i);
	}
	
	public void addSimilarity(Double similarity) {
		similarities.add(similarity);
	}

	public void setSimilarity(Double similarity, int i) {
		similarities.set(i, similarity);
	}

	public String getId() {
		return source.getId() + "::" + target.getId();
	}

	@Override
	public int compareTo(Link o) {
		int f = source.compareTo(o.getSource());
		if(f == 0)
			return target.compareTo(o.getTarget());
		return f;
	}
}
