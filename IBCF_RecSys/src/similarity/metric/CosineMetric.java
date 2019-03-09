/**
 * Compute the MSD similarity between profiles
 * 
 * 
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;

public class CosineMetric implements SimilarityMetric
{
	/**
	 * constructor - creates a new MeanSquaredDifferenceMetric object
	 */
	public CosineMetric()
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
        
        // initialize variable that will hold value of the total sum of products between the two profiles ratings of an item
        double sumOfProduct = 0;
        
        // iterate through set of common ids
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			// update total sum of the product of ratings for an item
			sumOfProduct += r1*r2;
		}
		
		// get the normalized rating value for each profile
		double p1_norm = p1.getNorm();
		double p2_norm = p2.getNorm();

		// return the Cosine similarity as long as product of p1_norm and p2_norm is greater than zero, else return zero as sim value
		return (p1_norm * p2_norm) > 0 ? sumOfProduct / (p1_norm * p2_norm) : 0;
	}
}
