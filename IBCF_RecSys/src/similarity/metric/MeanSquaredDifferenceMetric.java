/**
 * Compute the MSD similarity between profiles
 * 
 * 
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;

public class MeanSquaredDifferenceMetric implements SimilarityMetric
{
	/**
	 * constructor - creates a new MeanSquaredDifferenceMetric object
	 */
	public MeanSquaredDifferenceMetric()
	{
	}
		
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{        
		// Set of the common ids for the two profile args
        Set<Integer> common = p1.getCommonIds(p2); 
 
        // initialize variable that will hold value of the squared sum of the differences between the two profiles ratings of an item
        double sum_diff = 0; 
        
        // declare max rating and min rating variables
		double max_r = 5.0;
		double min_r = 0.5;
        
		// iterate through set of common ids
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			// update sum_diff variable by squaring the rating differences
			sum_diff += Math.pow((r1 - r2), 2);
		}
		// calculate the MSD and the squared difference between max and min rating for similarity calculation. Ensure no zero division
		double MSD = (common.size() > 0) ? sum_diff / common.size() : 0;
		double below = Math.pow((max_r - min_r), 2);

		// return similarity if denominator is greater than 0, else return similarity as zero.
		return (below > 0) ? 1 - (MSD / below) : 0;
	}
}
