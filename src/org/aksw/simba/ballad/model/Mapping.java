package org.aksw.simba.ballad.model;

import java.io.File;
import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Mapping {

	private String name;
	private File file;
	private TreeSet<Pair<String, String>> pairs = new TreeSet<>();

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

	public TreeSet<Pair<String, String>> getPairs() {
		return pairs;
	}

	public void setPairs(TreeSet<Pair<String, String>> pairs) {
		this.pairs = pairs;
	}

	public void addPair(String source, String target) {
		pairs.add(new Pair<String, String>(source, target));
	}
}

class Pair<F, S> {
	private String first; // first member of pair
	private String second; // second member of pair

	public Pair(String first, String second) {
		this.first = first;
		this.second = second;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}
}