package alg.ib.neighbourhood;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class ThresholdNeighbourhood extends Neighbourhood {
	
	private final double threshold;
	
	public ThresholdNeighbourhood(final double threshold) {
		super();
		this.threshold = threshold;
	}

	@Override
	public void computeNeighbourhoods(SimilarityMap simMap) {
		// iterate over each item
		for(Integer item_id: simMap.getIds()) 
		{
			// get the item similarity profile
			Profile profile = simMap.getSimilarities(item_id); 
			if(profile != null)
			{
				// iterate over each item in the profile
				for(Integer id: profile.getIds()) 
				{
					double sim = profile.getValue(id);
					// if similarity is > threshold then add to the neighbourhood
					if(sim > threshold)
						this.add(item_id, id);
				}
			}

		}
	}

}
