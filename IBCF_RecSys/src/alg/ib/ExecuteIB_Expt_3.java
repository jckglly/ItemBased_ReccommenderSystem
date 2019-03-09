package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.Predictor;
import similarity.metric.CosineMetric;
import similarity.metric.MeanSquaredDifferenceMetric;
import similarity.metric.PearsonMetric;
import similarity.metric.PearsonSigWeightingMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_3 {
	public static void main(String[] args)
	{
		// Similarity metric objects
		Object[] similarity_metrics = {new CosineMetric(), new PearsonMetric(), new PearsonSigWeightingMetric(50), new MeanSquaredDifferenceMetric()};
		
		// print headers of output in csv format to transfer easy to excel
		System.out.println("metric,RMSE,coverage");
		
		// iterate through the similarity metric object instances in the array
		for(Object sim_obj : similarity_metrics) {
			
			// configure the item-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
			Predictor predictor = new DeviationFromItemMeanPredictor();
			Neighbourhood neighbourhood = new NearestNeighbourhood(200);
			// cast the similarity metric object instance to SimilarityMetric type
			SimilarityMetric metric = (SimilarityMetric) sim_obj;
			
			// set the paths and filenames of the item file, genome scores file, train file and test file ...
			String folder = "ml-20m-2018-2019";
			String itemFile = folder + File.separator + "movies-sample.txt";
			String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
			String trainFile = folder + File.separator + "train.txt";
			String testFile = folder + File.separator + "test.txt";	
			
			// set the path and filename of the output file ...
			String outputFile = "results" + File.separator + "predictions_Expt3.txt";
			
			////////////////////////////////////////////////
			// Evaluates the CF algorithm (do not change!!):
			// - the RMSE (if actual ratings are available) and coverage are output to screen
			// - output file is created
			DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
			ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ibcf, reader.getTestData());
			
			// Write to output file
			eval.writeResults(outputFile);
			
			// Output the SimilarityMetric name (substring to avoid similarity.metric part of string),
			// as well as the RMSE and Coverage values. In csv format for easy tranferring.
			System.out.println(
					metric.getClass().getName().substring(18) + ',' +
					eval.getRMSE() + "," +
					eval.getCoverage());

		}
	}
}
