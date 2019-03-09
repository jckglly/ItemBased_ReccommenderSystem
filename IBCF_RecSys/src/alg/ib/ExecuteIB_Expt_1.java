package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.NonPersonalisedPredictor;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.SimpleAveragePredictor;
import alg.ib.predictor.WeightedAveragePredictor;
import similarity.metric.CosineMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_1 {
	public static void main(String[] args)
	{
		// Object array of the Predictor instances
		Object[] predictor_array = {new NonPersonalisedPredictor(), new SimpleAveragePredictor(), new WeightedAveragePredictor(), new DeviationFromItemMeanPredictor()};
		
		// print headers of output in csv format to transfer easy to excel
		System.out.println("predictor,k,RMSE,coverage");
		
		// iterate through the predictor object instances in the array
		for(Object pred_obj : predictor_array) {
			
			// k will be the neighbourhood size for Nearest Neighbourhood
			for(int k = 10; k <= 250; k += 10) {
				// configure the item-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
				// cast the predictor object instance to Predictor type
				Predictor predictor = (Predictor) pred_obj;
				Neighbourhood neighbourhood = new NearestNeighbourhood(k);
				SimilarityMetric metric = new CosineMetric();
				
				// set the paths and filenames of the item file, genome scores file, train file and test file ...
				String folder = "ml-20m-2018-2019";
				String itemFile = folder + File.separator + "movies-sample.txt";
				String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
				String trainFile = folder + File.separator + "train.txt";
				String testFile = folder + File.separator + "test.txt";	
				
				// set the path and filename of the output file ...
				String outputFile = "results" + File.separator + "predictions_Expt1.txt";
				
				////////////////////////////////////////////////
				// Evaluates the CF algorithm (do not change!!):
				// - the RMSE (if actual ratings are available) and coverage are output to screen
				// - output file is created
				DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
				ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
				Evaluator eval = new Evaluator(ibcf, reader.getTestData());
				
				// Write to output file
				eval.writeResults(outputFile);
				
				// Output the Predictor name (substring to avoid alg.ib.neighbourhood part of string),
				// as well as the neighbourhood size and RMSE and Coverage values. In csv format for easy tranferring.
				System.out.println(
						predictor.getClass().getName().substring(17) + ',' +
						k + "," + 
						eval.getRMSE() + "," +
						eval.getCoverage());
			}
		}
	}	
}
