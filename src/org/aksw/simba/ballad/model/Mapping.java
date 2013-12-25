package org.aksw.simba.ballad.model;

import java.io.File;
import java.util.TreeSet;

import org.aksw.simba.ballad.util.Convention;

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
	private TreeSet<String> labels = new TreeSet<String>();

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

	public TreeSet<String> getLabels() {
		return labels;
	}

	public void addLabel(String source, String target) {
		labels.add(Convention.toID(source, target));
	}

}