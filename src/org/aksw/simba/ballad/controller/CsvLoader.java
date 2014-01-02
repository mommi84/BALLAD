package org.aksw.simba.ballad.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.aksw.simba.ballad.Bundle;
import org.aksw.simba.ballad.model.Dataset;
import org.aksw.simba.ballad.model.Mapping;
import org.aksw.simba.ballad.model.Property;
import org.aksw.simba.ballad.model.Resource;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class CsvLoader implements Loader {

	@Override
	public void fillDataset(Dataset d) throws IOException {
		
		CSVReader reader = new CSVReader(new FileReader(d.getFile()));
		String[] titles = reader.readNext(); // gets the column titles
		ArrayList<Property> props = new ArrayList<Property>();
		for(int i=0; i<titles.length; i++) {
			int type = Bundle.getArrayValue(d.getStatus() + "_types", i);
			Property p = new Property(titles[i], type);
			props.add(p);
			d.addProperty(p);
		}
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			Resource r = new Resource(nextLine[0]);
			for (int i = 0; i < nextLine.length; i++) {
				if (nextLine[i] != null)
					r.setPropertyValue(props.get(i), nextLine[i]);
				else
					r.setPropertyValue(props.get(i), "");
			}
			d.addResource(r);
		}
		reader.close();
		
	}

	@Override
	public void fillMapping(Mapping m) throws IOException {

		CSVReader reader = new CSVReader(new FileReader(m.getFile()));
		reader.readNext(); // skips the column titles
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			m.addLabel(nextLine[0], nextLine[1]);
		}
		reader.close();

	}

}
