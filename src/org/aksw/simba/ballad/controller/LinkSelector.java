package org.aksw.simba.ballad.controller;

import java.util.ArrayList;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Resource;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class LinkSelector {
	
	public static final double SELECTION_RATE = 0.1;

	public static TreeSet<Link> select(Join join) {
		
		Dataset source = join.getSource();
		Dataset target = join.getTarget(); 
		TreeSet<Link> selection = new TreeSet<>();
		ArrayList<Resource> srcList = new ArrayList<>(source.getResources());
		ArrayList<Resource> tgtList = new ArrayList<>(target.getResources());
		int selSize = (int)(srcList.size() * tgtList.size() * SELECTION_RATE);
		for(int i=0; i<selSize; i++) {
			Resource s = srcList.get((int) (Math.random() * srcList.size()));
			Resource t = tgtList.get((int) (Math.random() * tgtList.size()));
			Link l = new Link(s, t);
			if(!selection.contains(l))
				selection.add(l);
		}
		
		return selection;
	}

}
