package org.aksw.simba.ballad.controller;

import org.aksw.simba.ballad.Bundle;
import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Property;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class PropertyAligner {

	public PropertyAligner() {
		super();
	}
	
	public void align(Dataset dS, Dataset dT) {
		String[] spa = Bundle.getString("source_property_align").split(",");
		String[] tpa = Bundle.getString("target_property_align").split(",");
	
		for(String strS : spa) {
			int indexS = Integer.parseInt(strS);
			for(String strT : tpa) {
				int indexT = Integer.parseInt(strT);
				if(indexS == indexT) {
					Property pS = dS.getProperty(indexS);
					Property pT = dT.getProperty(indexT);
					pS.addAlignment(pT);
					pT.addAlignment(pS);
				}
			}
		}
	}
}
