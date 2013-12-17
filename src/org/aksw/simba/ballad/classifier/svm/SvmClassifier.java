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
	
	protected int kernelType;
	protected int degree;
	
	public SvmClassifier(String trainFile, String testFile) {

		this.setTrainFile(trainFile);
		this.setTestFile(testFile);

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
					x[i][j-1].value = Double.parseDouble(nextLine[j]);
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
		svm_parameter parameter = new svm_parameter();
		parameter.svm_type = svm_parameter.C_SVC;
		parameter.kernel_type = kernelType;
		parameter.eps = 1E-3;
		parameter.C = 1E+6;
		parameter.gamma = 1;
		
		if(kernelType == svm_parameter.POLY)
			parameter.degree = degree;
		
		// build classifier
		model = svm.svm_train(problem, parameter);

		// evaluate test set
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
					instance[j-1].value = Double.parseDouble(nextLine[j]);
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
		
		precision = tp / (tp+fp) * 100.0;
		recall = tp / (tp+fn) * 100.0;
		fscore = 2 * precision * recall / (precision + recall);
		
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
	public void printDetails() {

		System.out.println("tp = " + tp + "\tfp = " + fp);
		System.out.println("tn = " + tn + "\tfn = " + fn);
		System.out.println("pr% = " + precision + "\nrc% = " + recall);
		System.out.println("fscore% = " + fscore);

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
