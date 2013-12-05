package org.aksw.simba.ballad.controller;

import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class FeatureHandler {

	public static void run(String joinName, Join join) {
		
		// if can't find joinName.features file, build it
		Dataset source = join.getSource();
		Dataset target = join.getTarget();
		
		// TODO
	}
	
}
