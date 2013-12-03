package org.aksw.simba.ballad.model;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Join {
	
	private Dataset source;
	private Dataset target;

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

	public Join(Dataset source, Dataset target) {
		super();
		this.source = source;
		this.target = target;
	}
}