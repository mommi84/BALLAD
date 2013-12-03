package org.aksw.simba.ballad.model;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Link {

	public static final int LABEL_NO = -1;
	public static final int UNLABELLED = 0;
	public static final int LABEL_YES = 1;

	private Resource source;
	private Resource target;
	private Double similarity = 0.0;
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

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public String getId() {
		return source.getId() + "::" + target.getId();
	}
}