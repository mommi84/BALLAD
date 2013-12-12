package org.aksw.simba.ballad.model;

import java.util.ArrayList;

import org.aksw.simba.ballad.similarity.Similarity;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class PropertyAlignment {
	
	private String name;
	private int type;
	private ArrayList<Property> sourceProperties = new ArrayList<>();
	private ArrayList<Property> targetProperties = new ArrayList<>();
	private ArrayList<Similarity> similarities = new ArrayList<>();
	
	public PropertyAlignment(String name, int type) {
		this.name = name;
	}
	
	public void addSimilarity(Similarity similarity) {
		similarities.add(similarity);
	}
	
	public Similarity getSimilarityAt(int i) {
		return similarities.get(i);
	}
	
	public void addSourceProperty(Property p) {
		sourceProperties.add(p);
	}

	public void addTargetProperty(Property p) {
		targetProperties.add(p);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Property> getSourceProperties() {
		return sourceProperties;
	}

	public void setSourceProperties(ArrayList<Property> sourceProperties) {
		this.sourceProperties = sourceProperties;
	}

	public ArrayList<Similarity> getSimilarities() {
		return similarities;
	}

	public void setSimilarities(ArrayList<Similarity> similarities) {
		this.similarities = similarities;
	}

	public ArrayList<Property> getTargetProperties() {
		return targetProperties;
	}

	public void setTargetProperties(ArrayList<Property> targetProperties) {
		this.targetProperties = targetProperties;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
