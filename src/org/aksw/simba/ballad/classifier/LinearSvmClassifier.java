package org.aksw.simba.ballad.classifier;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import libsvm.svm_node;
import au.com.bytecode.opencsv.CSVReader;


/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public class LinearSvmClassifier implements BasicClassifier {
	
	private double tp, tn, fp, fn, precision, recall, fscore;
	private String trainFile;
	private String testFile;

	public LinearSvmClassifier(String trainFile, String testFile) {
		
		this.setTrainFile(trainFile);
		this.setTestFile(testFile);
		
		run();
		
	}

	@Override
	public void run() {

		// load training set from file
		try {
			File f = new File(trainFile);
			
			// #instances = rows except header
			int size = countRows(trainFile)-1;
			
			CSVReader reader = new CSVReader(new FileReader(f));
			String[] titles = reader.readNext();
			
			// #features = columns except ID and class
	        svm_node[][] x = new svm_node[size][titles.length-2];
	        double[] y = new double[size];
			String[] nextLine;
			for (int i=0; (nextLine = reader.readNext()) != null; i++) {
				for (int j = 1; j < nextLine.length-2; j++) {
	                x[i][j] = new svm_node();
	                x[i][j].index = j;
	                x[i][j].value = Double.parseDouble(nextLine[j]);
				}
				y[i] = toDouble(nextLine[nextLine.length-1]);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// TODO build classifier, evaluate test set 
		
	}
	
	private double toDouble(String string) {
		if(string.equals("positive"))
			return 1.0;
		else // equals("negative")
			return 0.0;
	}

	private int countRows(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}

	@Override
	public String getDescription() {
		String s = "Linear Support Vector Machines (LibSVM)";
		return s;
	}

	@Override
	public double computeFscore() {
		// TODO Auto-generated method stub
		return fscore;
	}

	@Override
	public double getPrecision() {
		return precision;
	}

	@Override
	public double getRecall() {
		return recall;
	}

	@Override
	public double getFscore() {
		return fscore;
	}

	@Override
	public void printDetails() {
		
		System.out.println("tp = "+tp+"\tfp = "+fp);
		System.out.println("tn = "+tn+"\tfn = "+fn);
		System.out.println("pr% = "+precision+"\nrc% = "+recall);
		System.out.println("fscore% = "+fscore);
		
	}

	public String getTrainFile() {
		return trainFile;
	}

	public void setTrainFile(String trainFile) {
		this.trainFile = trainFile;
	}

	public String getTestFile() {
		return testFile;
	}

	public void setTestFile(String testFile) {
		this.testFile = testFile;
	}


}
