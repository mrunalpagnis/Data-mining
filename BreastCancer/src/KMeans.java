import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */

/**
 * @author MPagnis
 *
 */
public class KMeans {
	
	private static final Logger logger = Logger.getLogger(KMeans.class.getName());
	private static final boolean stop = false;
	
	int iterMax;
	double[][] predictors;
	int k;
	double[][] centroids;
	double[][] prevCentroids;
	double tau;
	
	List<ArrayList<Double>> clusters;
	
	public KMeans(double[][] predictors, int k, int iterMax, double tau) {
		
		
		this.predictors = predictors;
		
		this.k = k;
		
		this.iterMax = iterMax;
		
		this.tau = tau;
		
		centroids = new double[k][];
		prevCentroids = new double[k][];
		
		for(int i = 0; i<k; i++)
		{
			centroids[i] = new double[9];
			
			prevCentroids[i] = new double[9];
		}
		
		clusters = new ArrayList<ArrayList<Double>>();
		
	}
	
	public void initializeCentroids() {
		

		logger.log(Level.INFO, "Previous Centroids: \n");
		for(int i = 0; i<k; i++) {
			

			logger.log(Level.INFO, "Centroid: "+k+" \n");
			for(int j = 0; j<predictors[0].length; j++){
				
				int ran = new Random().nextInt(predictors.length);
				int ran2 = new Random().nextInt(predictors.length);
				centroids[i][j] = predictors[ran][j];
				prevCentroids[i][j] = predictors[ran2][j];
				
				logger.log(Level.INFO, "\t"+prevCentroids[i][j]);
				
			}
					
		}
				
	}
	
	public void cluster() {
		
		int i = 0;
		
		while(isStop(i)) {
			
			assignDataToCluster();
			
			recalculateCentroids();
			
			i++;
		}
	}
	

	private void assignDataToCluster() {
		
		for(int i=0; i<predictors.length; i++){
			
			double euclideanDistance[] = new double[k]; 
			for(int j=0; j<k; j++){
				
				euclideanDistance[j] = calculateEuclidean(i,j);
				
			}
			
		}
		
	}

	private double calculateEuclidean(int i, int j) {
		
		return 0;
	}

	private void recalculateCentroids() {
		
		
		
	}
	
	private boolean isStop(int i) {
					
		if(i>=iterMax)
			return stop;
		else if(precentChangeLETau())
			return stop;
		else
			return !stop;
	}

	private boolean precentChangeLETau() {
		
		double change = 0.0;
		
		for(int i = 0; i<k; i++){
			
			change = 0;
			for(int j = 0; j<9; j++){
				
				change = (centroids[i][j]/(centroids[i][j]+prevCentroids[i][j]));				
				
				if(change>tau)
					return false;
				else
					continue;
			}
			
		}
		
		return true;
	}
	
}
