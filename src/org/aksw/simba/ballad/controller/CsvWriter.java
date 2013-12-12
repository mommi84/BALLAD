package org.aksw.simba.ballad.controller;

import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class CsvWriter {
	
	CSVWriter writer;
	String path;
	
	public CsvWriter(String path) {
		this.path = path;
		try {
			writer = new CSVWriter(new FileWriter(path), ',');
		} catch (IOException e) {
			System.err.println("File "+path+" not found!");
		}
	}
	
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			System.err.println("Cannot close file "+path+"!");
		}
	}
	
	/**
	 * Write to CSV file.
	 * @param string a dash-separated string
	 */
	public void write(String string) {
		String[] entries = string.split("#");
		writer.writeNext(entries);
	}

}
