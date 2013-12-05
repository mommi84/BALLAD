package org.aksw.simba.ballad.controller;

import java.util.HashMap;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Mapping;
import org.aksw.simba.ballad.model.MappingUnit;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class Labeller {
	
	public static void label(TreeSet<Link> links, Mapping mapping) {
		
		TreeSet<MappingUnit<String, String>> units = mapping.getUnits();
		
		// index links and set default
		HashMap<String, Link> index = new HashMap<>();
		for(Link l : links) {
			index.put(l.getId(), l);
			l.setLabel(Link.LABEL_NO);
		}
		
		// label links
		for(MappingUnit<String, String> unit : units) {
			String id = unit.getFirst() + "::" + unit.getSecond();
			if(index.containsKey(id))
				index.get(id).setLabel(Link.LABEL_YES);
		}
				
	}
}
