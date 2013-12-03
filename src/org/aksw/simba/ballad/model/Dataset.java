package org.aksw.simba.ballad.model;

import java.io.File;
import java.util.TreeSet;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Dataset {

	/**
	 * Can be "source" or "target".
	 */
	private String status;
	private String name;
	private String url;
	private File file;

	private TreeSet<Resource> resources = new TreeSet<>();

	public Dataset(String status, String name, String url, File file) {
		super();
		this.status = status;
		this.name = name;
		this.url = url;
		this.file = file;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TreeSet<Resource> getResources() {
		return resources;
	}

	public void setResources(TreeSet<Resource> resources) {
		this.resources = resources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void addResource(Resource r) {
		resources.add(r);
	}
}
