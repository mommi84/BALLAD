package org.aksw.simba.ballad;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import org.aksw.simba.ballad.controller.CsvLoader;
import org.aksw.simba.ballad.controller.LinkSelector;
import org.aksw.simba.ballad.controller.PropertyAligner;
import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Mapping;

/**
 * BALLAD - Batch Learners Evaluation for Link Discovery.
 * 
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class Ballad {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// set properties
		Bundle.setBundleName(args[0]);

		Dataset source = new Dataset("source", Bundle.getString("source_name"),
				Bundle.getString("source_url"), new File(
						Bundle.getString("source_file")));
		Dataset target = new Dataset("target", Bundle.getString("source_name"),
				Bundle.getString("source_url"), new File(
						Bundle.getString("source_file")));
		Join join = new Join(source, target);
		Mapping mapping = new Mapping(join.getName(), new File(
				Bundle.getString("mapping_file")));

		CsvLoader loader = new CsvLoader();
		loader.fillDataset(source);
		loader.fillDataset(target);
		loader.fillMapping(mapping);

		// load property alignment
		PropertyAligner pa = new PropertyAligner();
		pa.align(source, target);
		
		// TODO join datasets and take 10%
		TreeSet<Link> trainingSet = LinkSelector.select(source, target);
		
		
	}

}
