package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class DeviationFromItemMeanPredictor implements Predictor {

	public DeviationFromItemMeanPredictor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) {
		
		//Initialize variable used to calculate the Deviation for Item Mean
		double above = 0;
		double below = 0;
		
		// Iterate through the ids of the users items
		for (Integer id: userProfileMap.get(userId).getIds())
		{
			// if the item is neighbour of the currents users item then execute
			if(neighbourhood.isNeighbour(itemId, id))
			{
				// initialize the similarity of the two items, the rating of the user item and get the mean rating for the user item
				Double sim = simMap.getSimilarity(itemId, id);
				Double r = userProfileMap.get(userId).getValue(id);
				Double avg_r = itemProfileMap.get(id).getMeanValue();
				above += sim.doubleValue() * (r.doubleValue() - avg_r.doubleValue());
				below += Math.abs(sim.doubleValue());
			}
		}
		// get the mean rating for the item 
		Double avg_item = itemProfileMap.get(itemId).getMeanValue();
		// ensure zero division does not occur by returning null if below < 0 else return the calculated predicted value
		return below > 0 ? avg_item + (above / below) : null;
	}
}
