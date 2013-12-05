package org.aksw.simba.ballad.model;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 * @param <F>
 * @param <S>
 */
public class MappingUnit<F, S> implements Comparable<MappingUnit<F, S>> {
	
	private String first; // first member of pair
	private String second; // second member of pair

	public MappingUnit(String first, String second) {
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

	@Override
	public int compareTo(MappingUnit<F, S> o) {
		int f = first.compareTo(o.getFirst());
		if(f == 0)
			return second.compareTo(o.getSecond());
		return f; 
	}
}
