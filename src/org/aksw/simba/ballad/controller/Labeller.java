package org.aksw.simba.ballad.controller;

import java.util.HashMap;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Mapping;

/**
 * To be used later maybe.
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Labeller {
	
	public static void label(TreeSet<Link> links, Mapping mapping) {
		
		TreeSet<String> labels = mapping.getLabels();
		
		// index links and set default
		HashMap<String, Link> index = new HashMap<String, Link>();
		for(Link l : links) {
			index.put(l.getId(), l);
			l.setLabel(Link.LABEL_NO);
		}
		
		// label links
		for(String label : labels) {
			if(index.containsKey(label))
				index.get(label).setLabel(Link.LABEL_YES);
		}
				
	}
}
