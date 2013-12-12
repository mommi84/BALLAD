package org.aksw.simba.ballad.model;

import java.util.ArrayList;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Join {
	
	private String name;
	private Dataset source;
	private Dataset target;
	private String setting;
	private ArrayList<PropertyAlignment> propertyAlignments = new ArrayList<>();

	public Join(Dataset source, Dataset target, String setting) {
		super();
		this.source = source;
		this.target = target;
		this.setSetting(setting);
		this.name = source.getName() + "::" + target.getName();
	}

	public void addPropertyAlignment(PropertyAlignment pa) {
		propertyAlignments.add(pa);
	}
	
	public Dataset getSource() {
		return source;
	}

	public void setSource(Dataset source) {
		this.source = source;
	}

	public Dataset getTarget() {
		return target;
	}

	public void setTarget(Dataset target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PropertyAlignment> getPropertyAlignments() {
		return propertyAlignments;
	}

	public void setPropertyAlignments(
			ArrayList<PropertyAlignment> propertyAlignments) {
		this.propertyAlignments = propertyAlignments;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}
}
