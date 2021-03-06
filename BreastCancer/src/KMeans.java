import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

	// private static final Logger logger =
	// Logger.getLogger(KMeans.class.getName());
	private static final boolean stop = false;

	private static final int exclude1 = 5;
	private static final int exclude2 = 7;
	
	int iterMax;
	double[][] predictors;
	int k;
	double[][] centroids;
	double[][] prevCentroids;
	double tau;
	int attributes = 9;

	List<ArrayList<double[]>> clusters;

	public KMeans(double[][] predictors, int k, int iterMax, double tau) {

		this.predictors = predictors;

		this.k = k;

		this.iterMax = iterMax;

		this.tau = tau;

		centroids = new double[k][];
		prevCentroids = new double[k][];
		clusters = new ArrayList<ArrayList<double[]>>();

		for (int i = 0; i < k; i++) {
			centroids[i] = new double[attributes];
			prevCentroids[i] = new double[attributes];
			clusters.add(new ArrayList<>());
		}

	}

	public void initializeCentroids() {

		// logger.log(Level.INFO, "Centroids: \n");

		Random r1 = new Random();
		for (int i = 0; i < k; i++) {

			double x[] = new double[10];
			int j = 0;
			// logger.log(Level.INFO, "Centroid: "+i+" \n");
			for (j = 0; j < attributes; j++) {

				centroids[i][j] = r1.nextInt(10) + 1;
				prevCentroids[i][j] = r1.nextInt(10) + 1;

				// System.out.print("\t"+centroids[i].length);

				x[j] = centroids[i][j];
			}

		}

	}

	public void cluster() {

		int i = 0;

		while (isStop(i)) {

			assignDataToCluster(predictors);

			recalculateCentroids();

			i++;
		}

		//printClusters();
	}

	private double printClusters() {
		

		int fp = 0;
		int tp = 0;
		for (int i = 0; i < k; i++) {
			
			int benign = 0;
			int mal = 0;
	
			ArrayList<double[]> datapoint = clusters.get(i);
			int j = 0;
			for (j = 0; j < datapoint.size(); j++) {
				if(datapoint.get(j)[datapoint.get(j).length-1]==2)
					benign++;
				else if(datapoint.get(j)[datapoint.get(j).length-1]==4)
					mal++;
			}
			
			System.out.println("Cluster "+i+" benign count: "+benign+" Malignant count: "+ mal+ " Total count: "+datapoint.size());
			if(benign>mal){
				fp += mal;
				tp += benign;
			}
			else{
				fp +=benign;
				tp +=mal;
			}
		}
		
		double ppv = (double)tp/(tp+fp);
		
		System.out.println("PPV value: "+ ppv+"\n");
		
		return ppv;
	}

	public void assignDataToCluster(double [][]predictors) {

		// empty previous clusters
		// clusters = new ArrayList<ArrayList<double[]>>();

		for (ArrayList<double[]> list : clusters) {
			list.clear();
		}

		for (int i = 0; i < predictors.length; i++) {

			double min = Double.POSITIVE_INFINITY;
			int index = 0;
			double euclideanDistance[] = new double[k];
			for (int j = 0; j < k; j++) {

				euclideanDistance[j] = calculateEuclidean(predictors, i, j);
				if (euclideanDistance[j] < min) {
					min = euclideanDistance[j];
					index = j;
				}
			}
			ArrayList<double[]> c = clusters.get(index);
			int l = c.size();
			double e[] = new double[10];
			for (int a = 0; a < 10; a++)
				e[a] = predictors[i][a];

			clusters.get(index).add(l, e);
			// System.out.println();
		}

	}

	private double calculateEuclidean(double [][]predictors,int i, int j) {

		double euclideanDist = 0.0;
		for (int a = 0; a < attributes; a++) {
//			if(a==exclude1)// & a!=exclude2)
			euclideanDist += (predictors[i][a] - centroids[j][a]) * (predictors[i][a] - centroids[j][a]);
		}

		return Math.sqrt(euclideanDist);
	}

	private void recalculateCentroids() {

		Iterator<ArrayList<double[]>> aIter = clusters.iterator();
		int cl = 0;
		while (aIter.hasNext()) {

			ArrayList<double[]> a = (ArrayList<double[]>) aIter.next();

			Iterator<double[]> dIter = a.iterator();
			double[] avg = new double[attributes];
			int n = 0;

			while (dIter.hasNext()) {

				double[] d = (double[]) dIter.next();

				n++;

				for (int atribute = 0; atribute < attributes; atribute++) {
//					if(atribute==exclude1)// & atribute!=exclude2)
					avg[atribute] += d[atribute];
				}
			}
			for (int atribute = 0; atribute < attributes; atribute++) {

				avg[atribute] /= n;
				centroids[cl][atribute] = avg[atribute];
			}
			cl++;
		}

	}

	private boolean isStop(int i) {

		if (i >= iterMax)
			return stop;
		// else if(precentChangeLETau())
		// return stop;
		else
			return !stop;
	}

	private boolean precentChangeLETau() {

		double change = 0.0;

		for (int i = 0; i < k; i++) {

			change = 0;
			for (int j = 0; j < attributes; j++) {

				change = Math.abs(centroids[i][j] - prevCentroids[i][j]);

				if (change > tau * prevCentroids[i][j])
					return false;
				else
					continue;
			}

		}

		return true;
	}


	public static double[][] readData(String filename) {

		// "C:\\Users\\mpagnis\\Documents\\personal\\R\\newclean.data"
		BufferedReader br = null;
		double[][] predictors = null;
		try {

			br = new BufferedReader(new FileReader(new File(filename)));
			predictors = new double[675][];
			String r;
			int i = 0;
			while ((r = br.readLine()) != null) {
				// logger.log(Level.INFO, "\nline: "+r);

				predictors[i] = new double[10];

				String atr[] = r.split(",");
				int j = 0;

				for (String s : atr) {

					predictors[i][j] = Double.parseDouble(s);
					j++;
				}
				i++;

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}

		return predictors;
	}
	public void crossValidate(double [][]predictors){
		
		double [][]train;
		double [][]test = new double[67][];
		int seed = 67;
		train = new double[675-seed][];
		for(int i = 0; i<seed; i++){
			test[i] = new double[10];
			test[i] = predictors[i];
		}
		for(int i=seed;i<675;i++){
			train[i] = new double[10];
			train[i] = predictors[i];
		}
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "C:\\Users\\mpagnis\\Documents\\personal\\R\\newclean.data";

		double[][] predictors = readData(filename);
		double [][]train;
		double [][]test; 
		int seed = 67;
		
		double totalPPV = 0.0;
		
		for(int v = 0; v<10; v++){
			
			test = new double[67][];
			train = new double[675-seed][];
			
			int tr = 0;
			int te = 0;
			
			int start = v*seed;
			
			for(int i = 0; i<675; i++){
				
				if(i>=start & i<start+seed){
					test[te] = new double[10];
					test[te] = predictors[i];
					te++;
				}
				else{
					train[tr] = new double[10];
					train[tr] = predictors[i];
					tr++;
				}
				
			}
			
			KMeans km = new KMeans(train,2,50,0.10);
			
			km.initializeCentroids();
			km.cluster();
			
			km.assignDataToCluster(test);
			
			System.out.println("Test = "+(v+1));
			totalPPV += km.printClusters();
		}
		
		System.out.println("Delta PPV: "+ totalPPV/10);

	}

}
