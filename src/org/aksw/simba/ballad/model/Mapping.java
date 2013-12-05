package org.aksw.simba.ballad.model;

import java.io.File;
import java.util.TreeSet;

/**
 * Mapping is a transitional class for storing perfect mappings loaded from a
 * file.
 * 
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Mapping {

	private String name;
	private File file;
	private TreeSet<MappingUnit<String, String>> units = new TreeSet<>();

	public Mapping(String name, File file) {
		super();
		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public TreeSet<MappingUnit<String, String>> getUnits() {
		return units;
	}

	public void addUnit(String source, String target) {
		units.add(new MappingUnit<String, String>(source, target));
	}

}