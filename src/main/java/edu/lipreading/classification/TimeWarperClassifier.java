package edu.lipreading.classification;

import java.util.List;

import edu.lipreading.Constants;
import edu.lipreading.Sample;
import edu.lipreading.Utils;

public class TimeWarperClassifier implements Classifier{
	private List<Sample> trainingSet;
	private List<String> vocabulary = Constants.VOCABULARY;
	
	
	@Override
	public String test(Sample test) {
		double [] results = new double[vocabulary.size()];
		int [] counts = new int[vocabulary.size()];
		TimeWarper tw = new TimeWarper();
			for (Sample training : trainingSet) {
				if(!test.equals(training)){
					for (int i = 0; i < vocabulary.size(); i++) {
						if(training.getId().contains(vocabulary.get(i))){
							results[i] += tw.dtw(test, training);
							counts[i]++;
						}
					}
				}
			}
			for (int i = 0; i < vocabulary.size(); i++) {
				results[i] /= counts[i];
			}
			int minIndex = Utils.getMinIndex(results);
			return vocabulary.get(minIndex);
	}


	@Override
	public void train(List<Sample> trainingSet) {
		this.trainingSet = trainingSet;
		
	}


	@Override
	public void update(Sample train) {
		this.trainingSet.add(train);
	}

}
