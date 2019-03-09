package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.neighbourhood.ThresholdNeighbourhood;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.Predictor;
import similarity.metric.CosineMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_2 {
	public static void main(String[] args)
	{
		// print headers of output in csv format to transfer easy to excel
		System.out.println("DeviationFromItemMeanPredictor:" + "\nthreshold,RMSE,coverage");
		
		// iterate threshold values
		for(int t = 0; t <= 70; t += 5) {
			// convert threshold int to double
			Double threshold = t * 0.01;
			// configure the item-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
			Predictor predictor = new DeviationFromItemMeanPredictor();
			Neighbourhood neighbourhood = new ThresholdNeighbourhood(threshold);
			SimilarityMetric metric = new CosineMetric();
			
			// set the paths and filenames of the item file, genome scores file, train file and test file ...
			String folder = "ml-20m-2018-2019";
			String itemFile = folder + File.separator + "movies-sample.txt";
			String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
			String trainFile = folder + File.separator + "train.txt";
			String testFile = folder + File.separator + "test.txt";	
			
			// set the path and filename of the output file ...
			String outputFile = "results" + File.separator + "predictions_Expt2.txt";
			
			////////////////////////////////////////////////
			// Evaluates the CF algorithm (do not change!!):
			// - the RMSE (if actual ratings are available) and coverage are output to screen
			// - output file is created
			DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
			ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ibcf, reader.getTestData());
			
			// Write to output file
			eval.writeResults(outputFile);
			
			// Output neighbourhood size and RMSE and Coverage values. In csv format for easy tranferring.
			System.out.println(
					threshold.doubleValue() + "," + 
					eval.getRMSE() + "," +
					eval.getCoverage());
		}
	}
}
