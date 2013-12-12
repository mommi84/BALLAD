package org.aksw.simba.ballad.controller;

import java.util.HashMap;

import org.aksw.simba.ballad.Bundle;
import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.PropertyAlignment;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class PropertyAligner {

	public static void align(Join join) {
		
		Dataset source = join.getSource();
		Dataset target = join.getTarget();
		
		String[] spa = Bundle.getString("source_property_align").split(",");
		String[] tpa = Bundle.getString("target_property_align").split(",");
		
		HashMap<Integer, PropertyAlignment> index = new HashMap<>();
		
		// create alignments and link source properties
		for(String strS : spa) {
			int indexS = Integer.parseInt(strS);
			if(!index.containsKey(indexS)) {
				Property property = source.getProperty(indexS);
				PropertyAlignment pa = new PropertyAlignment(null, property.getType());
				pa.addSourceProperty(property);
				index.put(indexS, pa);
			} else
				index.get(indexS).addSourceProperty(source.getProperty(indexS));
		}
		
		// link target properties
		for(String strT : tpa) {
			int indexT = Integer.parseInt(strT);
			if(index.containsKey(indexT))
				index.get(indexT).addTargetProperty(target.getProperty(indexT));
		}
		
		for(Integer i : index.keySet()) {
			PropertyAlignment pa = index.get(i);
			// only add alignments with some target properties
			if(pa.getTargetProperties().size() > 0) {
				join.addPropertyAlignment(pa);
				// link them to their properties
				for(Property p : pa.getSourceProperties())
					p.setPropertyAlignment(pa);
				for(Property p : pa.getTargetProperties())
					p.setPropertyAlignment(pa);
				// set their names
				pa.setName(pa.getSourceProperties().get(0).getName() + "::" +
						pa.getTargetProperties().get(0).getName());
			}
		}	
	}
}
