package org.aksw.simba.ballad;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import org.aksw.simba.ballad.controller.CsvLoader;
import org.aksw.simba.ballad.controller.FeatureHandler;
import org.aksw.simba.ballad.controller.Labeller;
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

		String setting = args[0];
		
		// set properties
		Bundle.setBundleName(setting);

		Dataset source = new Dataset("source", Bundle.getString("source_name"),
				Bundle.getString("source_url"), new File(
						Bundle.getString("source_file")));
		Dataset target = new Dataset("target", Bundle.getString("target_name"),
				Bundle.getString("target_url"), new File(
						Bundle.getString("target_file")));
		Join join = new Join(source, target);
		Mapping mapping = new Mapping(join.getName(), new File(
				Bundle.getString("mapping_file")));

		CsvLoader loader = new CsvLoader();
		loader.fillDataset(source);
		System.out.println("source dataset loaded");
		loader.fillDataset(target);
		System.out.println("target dataset loaded");
		loader.fillMapping(mapping);
		System.out.println("mapping loaded");

		// load property alignment
		PropertyAligner.align(source, target);
		System.out.println("properties aligned");
		
		// select training set and label instances
		TreeSet<Link> trainingSet = LinkSelector.select(join);
		System.out.println("links selected");
		Labeller.label(trainingSet, mapping);
		System.out.println("links labelled");
		
		// TODO compute features
		FeatureHandler.run(setting, join);
	}

}
