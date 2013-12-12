package org.aksw.simba.ballad.model;


/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Property implements Comparable<Property> {

	public static final int TYPE_STRING = 0;
	public static final int TYPE_NUM = 1;
	public static final int TYPE_DATE = 2;
	private String name;
	private int type;
	private PropertyAlignment propertyAlignment;

	public Property(String name, int type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public int compareTo(Property o) {
		return name.compareTo(o.getName());
	}

	public PropertyAlignment getPropertyAlignment() {
		return propertyAlignment;
	}

	public void setPropertyAlignment(PropertyAlignment propertyAlignment) {
		this.propertyAlignment = propertyAlignment;
	}

}
