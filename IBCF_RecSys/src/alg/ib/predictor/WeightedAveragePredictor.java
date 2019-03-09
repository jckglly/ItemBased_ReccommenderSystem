package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class WeightedAveragePredictor implements Predictor {

	public WeightedAveragePredictor() {
	}

	@Override
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) {
		
		// initialise variables to 0
		double above = 0;
		double below = 0;
		
		// iterate over the  user's items ids
		for(Integer id: userProfileMap.get(userId).getIds()) 
		{
			// if the item and the users it id are in the neighbourhood then execute
			if(neighbourhood.isNeighbour(itemId, id)) 
			{
				// get the user ratings for an item and get similarity for the item and the user item id
				Double rating = userProfileMap.get(userId).getValue(id);
				Double sim_users = simMap.getSimilarity(itemId, id);
				above += (sim_users.doubleValue() * rating.doubleValue());
				below += Math.abs(sim_users);
			}
		}
		// if below is less than zero then return null else return the double value of the division
		return (below > 0) ? new Double(above / below) : null;
	}

}
