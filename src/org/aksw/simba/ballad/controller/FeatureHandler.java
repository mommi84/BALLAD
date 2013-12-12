package org.aksw.simba.ballad.controller;

import java.util.ArrayList;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.Resource;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class FeatureHandler {

	public static void run(String setting, Join join) {
		
		// if can't find <setting>.features file, build it
		Dataset source = join.getSource();
		Dataset target = join.getTarget();
		
		// TODO
		TreeSet<Resource> src = source.getResources();
		TreeSet<Resource> tgt = target.getResources();
		
		ArrayList<Property> propSrc = source.getProperties();		
		
		for(Resource s : src) {
			for(Resource t : tgt) {
				Link l = new Link(s, t);
				for(Property ps : propSrc) {
					// TODO one-to-many approach
					Property pt = ps.getAlignment(0);
					// TODO compute similarity
					Double sim = 0.0;
					l.addSimilarity(sim);
				}
			}
		}
	}
	
}
