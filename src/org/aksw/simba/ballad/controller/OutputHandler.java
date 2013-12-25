package org.aksw.simba.ballad.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Join;
import org.aksw.simba.ballad.model.Link;
import org.aksw.simba.ballad.model.Mapping;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.PropertyAlignment;
import org.aksw.simba.ballad.model.Resource;
import org.aksw.simba.ballad.similarity.CosineSimilarity;
import org.aksw.simba.ballad.similarity.NumericSimilarity;
import org.aksw.simba.ballad.similarity.Similarity;
import org.aksw.simba.ballad.similarity.WeightedEditSimilarity;
import org.aksw.simba.ballad.similarity.WeightedNgramSimilarity;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class OutputHandler {

	public static final double SELECTION_RATE = 0.1;
	private static TreeSet<Link> trainingSet = new TreeSet<Link>();
	private Join join;
	private Mapping mapping;
	private TreeSet<String> labels;
	private TreeSet<Resource> src, tgt;
	private ArrayList<PropertyAlignment> alignments;
	private HashMap<Integer, ArrayList<Similarity>> simMap = new HashMap<Integer, ArrayList<Similarity>>();
	private CsvWriter testWriter;
	private CsvWriter trainWriter;
	private String trainFile, testFile;

	public OutputHandler(Join join, Mapping mapping) {
	
		this.setJoin(join);
		this.setMapping(mapping);
		run();
	}

	private void run() {
		
		labels = mapping.getLabels();
		trainFile = "features/"+join.getSetting()+"_train.features.csv";
		testFile = "features/"+join.getSetting()+"_test.features.csv";
		
		// TODO load these from config file
		ArrayList<Similarity> stringSims = new ArrayList<Similarity>();
		stringSims.add(new WeightedNgramSimilarity(3));
		stringSims.add(new WeightedEditSimilarity());
		stringSims.add(new CosineSimilarity());
		simMap.put(Property.TYPE_STRING, stringSims);
		ArrayList<Similarity> numSims = new ArrayList<Similarity>();
		numSims.add(new NumericSimilarity());
		simMap.put(Property.TYPE_NUM, numSims);
		simMap.put(Property.TYPE_DATE, new ArrayList<Similarity>());
		
		// collect random link IDs for training set
		Dataset source = join.getSource();
		Dataset target = join.getTarget();
		ArrayList<Resource> srcList = new ArrayList<Resource>(source.getResources());
		ArrayList<Resource> tgtList = new ArrayList<Resource>(target.getResources());
		int selSize = (int) (srcList.size() * tgtList.size() * SELECTION_RATE);
		for (int i = 0; i < selSize; i++) {
			Resource s = srcList.get((int) (Math.random() * srcList.size()));
			Resource t = tgtList.get((int) (Math.random() * tgtList.size()));
			Link l = new Link(s, t);
			if (!trainingSet.contains(l))
				trainingSet.add(l);
		}
	
		// get resources and source properties
		src = source.getResources();
		tgt = target.getResources();
	
		// get property alignments
		alignments = join.getPropertyAlignments();
		for (PropertyAlignment p : alignments) {
			int type = p.getType();
			for(Similarity sim : simMap.get(type)) {
				// TODO .prepare method
				if(sim instanceof NumericSimilarity)
					((NumericSimilarity) sim).computeExtrema(join, p);
				p.addSimilarity(sim);
			}
				
		}
	}

	public void computeTrainingSet(boolean forceOverwrite) {
		
		if(new File(trainFile).isFile())
			return;
		
		trainWriter = new CsvWriter(trainFile);
		trainWriter.write(buildTitleString());
		
		// saves training set
		for(Link l : trainingSet)
			trainWriter.write(buildOutString(l));
		
		trainWriter.close();
	
	}

	public void computeTestSet(boolean forceOverwrite) {
		
		if(new File(testFile).isFile())
			return;
		
		testWriter = new CsvWriter(testFile);
		testWriter.write(buildTitleString());
		
		// saves test set
		for (Resource s : src)
			for (Resource t : tgt)
				testWriter.write(buildOutString(new Link(s, t)));
		
		testWriter.close();
	
	}

	private String buildTitleString() {
		
		String out = "id#";
		for (PropertyAlignment pa : alignments)
			for(Similarity sim : pa.getSimilarities())
				out += sim.getName()+" "+pa.getName()+"#";
		out += "class";
		
		return out;
		
	}

	private String buildOutString(Link l) {
		
		Resource s = l.getSource();
		Resource t = l.getTarget();
		
		// create a string to be written on the file
		String out = l.getId();
		// iterate among property alignments
		for (PropertyAlignment pa : alignments) {
			// iterate among similarities
			for(Similarity sim : pa.getSimilarities()) {
				// compute similarity
				// TODO the current approach ignores one-to-many alignments
				String term1 = s.getPropertyValue(pa.getSourceProperties().get(0));
				String term2 = t.getPropertyValue(pa.getTargetProperties().get(0));
				Double val = sim.compute(term1, term2);
				out += "#" + val;
			}
		}
		
		// add class
		String clax = labels.contains(l.getId()) ? "positive" : "negative";
		out += "#" + clax;
		
		return out;
	}

	public String getTrainFile() {
		return trainFile;
	}

	public String getTestFile() {
		return testFile;
	}

	public Join getJoin() {
		return join;
	}

	public void setJoin(Join join) {
		this.join = join;
	}

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

}
