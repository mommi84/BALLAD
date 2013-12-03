package org.aksw.simba.ballad.controller;

import java.io.IOException;

import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Mapping;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface Loader {
	
	public void fillDataset(Dataset d) throws IOException;
	
	public void fillMapping(Mapping m) throws IOException;
	
}
