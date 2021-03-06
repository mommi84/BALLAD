package org.aksw.simba.ballad.classifier.svm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.aksw.simba.ballad.classifier.BasicClassifier;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 * 
 */
public abstract class SvmClassifier implements BasicClassifier {

	private double tp, tn, fp, fn, precision, recall, fscore;
	private String trainFile;
	private String testFile;
	
	private svm_problem problem;
	private svm_model model;
	private svm_parameter parameter;
	
	protected int kernelType;
	protected int degree;
	
	public SvmClassifier(String trainFile, String testFile) {

		this.setTrainFile(trainFile);
		this.setTestFile(testFile);
		
		parameter = new svm_parameter();
		// default values
		parameter.C = 1E+6;
		parameter.eps = 1E-3;
		parameter.gamma = 1;
	}
	
	public void setC(double val) {
		parameter.C = val;
	}

	public void setEps(double val) {
		parameter.eps = val;
	}
	
	public void setGamma(double val) {
		parameter.gamma = val;
	}

	@Override
	public void run() {

		int size;		
		svm_node[][] x;
		double[] y;
		
		// load training set from file
		try {
			File f = new File(trainFile);

			// #instances = rows except header
			size = countRows(trainFile) - 1;
			
			CSVReader reader = new CSVReader(new FileReader(f));
			String[] titles = reader.readNext();

			// #features = columns except ID and class
			x = new svm_node[size][titles.length - 2];
			y = new double[size];
			String[] nextLine;
			for (int i = 0; (nextLine = reader.readNext()) != null; i++) {
				for (int j = 1; j < nextLine.length - 1; j++) {
					x[i][j-1] = new svm_node();
					x[i][j-1].index = j-1;
					double val = Double.parseDouble(nextLine[j]);
					// SVM cannot handle NaN values
					if(Double.isNaN(val))
						x[i][j-1].value = 0;
					else
						x[i][j-1].value = val;
				}
				y[i] = toDouble(nextLine[nextLine.length - 1]);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
				
		// configure model
		problem = new svm_problem();
		problem.l = size;
		problem.x = x;
		problem.y = y;
		parameter.svm_type = svm_parameter.C_SVC;
		parameter.kernel_type = kernelType;
		
		if(kernelType == svm_parameter.POLY)
			parameter.degree = degree;
		
		// build classifier
		model = svm.svm_train(problem, parameter);

		// evaluate test set
		tp = tn = fp = fn = 0;
		try {
			File f = new File(testFile);

			CSVReader reader = new CSVReader(new FileReader(f));
			String[] nextLine = reader.readNext(); // skip column titles
			int nfeat = nextLine.length - 2;
			while ((nextLine = reader.readNext()) != null) {
				svm_node[] instance = new svm_node[nfeat];
				for (int j = 1; j < nextLine.length - 1; j++) {
					instance[j-1] = new svm_node();
					instance[j-1].index = j-1;
					double val = Double.parseDouble(nextLine[j]);
					// SVM cannot handle NaN values
					if(Double.isNaN(val))
						instance[j-1].value = 0;
					else
						instance[j-1].value = val;
				}
				double theoretic = toDouble(nextLine[nextLine.length - 1]);
				double predicted = svm.svm_predict(model, instance);
				if(theoretic == 1) {
					if(predicted == 1)
						tp++;
					else
						fn++;
				} else {
					if(predicted == 1)
						fp++;
					else
						tn++;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		precision = (tp+fp == 0) ? 0.0 : tp / (tp+fp) * 100.0;
		recall = (tp+fp == 0) ? 0.0 : tp / (tp+fn) * 100.0;
		fscore = (precision+recall == 0) ? 0.0 : 2 * precision * recall / (precision + recall);
		
	}

	private double toDouble(String string) {
		if (string.equals("positive"))
			return 1.0;
		else
			// equals("negative")
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
	public String getDetails() {
		
		String details = "";
		details += "tp = " + tp + "\tfp = " + fp + "\n";
		details += "tn = " + tn + "\tfn = " + fn + "\n";
		details += "pr% = " + precision + "\nrc% = " + recall + "\n";
		details += "fscore% = " + fscore;
		return details;

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
