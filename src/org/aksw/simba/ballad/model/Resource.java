package org.aksw.simba.ballad.model;

import java.util.HashMap;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Resource implements Comparable<Resource> {

	private String id;
	private HashMap<Property, String> attr = new HashMap<>();

	public Resource(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<Property, String> getAttr() {
		return attr;
	}

	public void setAttr(HashMap<Property, String> attr) {
		this.attr = attr;
	};

	public String getPropertyValue(Property p) {
		return attr.get(p);
	}

	public void setPropertyValue(Property p, String v) {
		attr.put(p, v);
	}

	@Override
	public int compareTo(Resource o) {
		return id.compareTo(o.getId());
	}
}
