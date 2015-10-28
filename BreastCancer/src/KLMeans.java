import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * @author MPagnis
 *
 */
public class KLMeans {

	// private static final Logger logger =
	// Logger.getLogger(KMeans.class.getName());
	private static final boolean stop = false;

//	private static final int exclude1 = 5;
//	private static final int exclude2 = 7;
	
	int iterMax;
	double[][] predictors;
	int k;
	int l;
	double[][] centroids;
	double[][] prevCentroids;
	double tau;
	int attributes = 10;

	List<ArrayList<double[]>> clusters;
	List<ArrayList<Double>> membership;

	public KLMeans(double[][] predictors, int k, int l, int iterMax, double tau) {

		this.predictors = predictors;

		this.k = k;

		this.l = l; 
		
		this.iterMax = iterMax;

		this.tau = tau;

		centroids = new double[k][];
		prevCentroids = new double[k][];
		clusters = new ArrayList<ArrayList<double[]>>();
		membership = new ArrayList<ArrayList<Double>>();

		for (int i = 0; i < k; i++) {
			centroids[i] = new double[attributes];
			prevCentroids[i] = new double[attributes];
			clusters.add(new ArrayList<>());
			membership.add(new ArrayList<>());
		}

	}

	public void initializeCentroids() {

		// logger.log(Level.INFO, "Centroids: \n");

		Random r1 = new Random();
		for (int i = 0; i < k; i++) {

//			double x[] = new double[10];
			int j = 0;
			// logger.log(Level.INFO, "Centroid: "+i+" \n");
			for (j = 0; j < attributes; j++) {

				centroids[i][j] = r1.nextInt(10) + 1;
				prevCentroids[i][j] = r1.nextInt(10) + 1;

				// System.out.print("\t"+centroids[i].length);

//				x[j] = centroids[i][j];
			}

		}

	}

	public void cluster() {

		int i = 0;

		while (isStop(i)) {

			assignDataToCluster();

			recalculateCentroids();

			i++;
		}

//		printClusters();
	}

	private double printClusters() {
		

		int fp = 0;
		int tp = 0;
		ArrayList<String> b = new ArrayList<String>();
		ArrayList<String> m = new ArrayList<String>();
		
		for (int i = 0; i < k; i++) {
			
			int benign = 0;
			int mal = 0;

//			System.out.print("Cluster " + i + " : [ ");
			
			
			ArrayList<double[]> datapoint = clusters.get(i);
			int j = 0;
			b.clear();
			m.clear();
			
			for (j = 0; j < datapoint.size(); j++) {
				// logger.log(Level.INFO, ""+datapoint.get(j)[9]);
//				System.out.print(" " + datapoint.get(j)[9]);
				if(datapoint.get(j)[datapoint.get(j).length-1]==2){
					
					benign++;
					b.add(""+datapoint.get(j)[0]);
				}
				if(datapoint.get(j)[datapoint.get(j).length-1]==4){
					
					mal++;
					m.add(""+datapoint.get(j)[0]);
				}
			}
//			System.out.println(" ] \n" + j);
			
			System.out.println("Cluster "+i+" benign count: "+benign+" Malignant count: "+ mal+ " Total count: "+datapoint.size());
			if(benign>mal){
				fp += mal;
				tp += benign;
//				System.out.println("False Positives: (Malignant classified as Benign)\n"+m+"\n");
			}
			else{
				fp +=benign;
				tp +=mal;
//				System.out.println("False Positives: (Benign classified as Malignant)\n"+b+"\n");
			}
			System.out.println("Classified as benign: "+b);
			System.out.println("Classified as Malignant: "+m+"\n");
			
		}
		
		double ppv = (double)tp/(tp+fp);
		
		System.out.println("PPV value: "+ ppv+"\n\n");
		
		return ppv;
	}

	public void assignDataToCluster() {

		// empty previous clusters
		// clusters = new ArrayList<ArrayList<double[]>>();

		for (ArrayList<double[]> list : clusters) {
			list.clear();
		}
		
		for(ArrayList<Double> list : membership){
			list.clear();
		}

		for (int i = 0; i < predictors.length; i++) {

			double[] min = new double[l];
			Arrays.fill(min , Double.MAX_VALUE);
			int index[] = new int[l];
			Arrays.fill(index , 0);
			List<Double> euclideanDistance = new ArrayList<Double>();
			
			for (int j = 0; j < k; j++) {

				double d = calculateEuclidean( i, j);
				euclideanDistance.add(d);
				if (d < min[l-1]) {
					
					
					min[l-1] = d;
					
					Arrays.sort(min);				
					
				}
			}
			
			
			double sum = 0;
			for(int kl=0;kl<l;kl++){
				sum += min[kl];
				index[kl] = euclideanDistance.indexOf(min[kl]);
			}
			
			for(int kl=0;kl<l;kl++){
				ArrayList<double[]> c = clusters.get(index[kl]);
				ArrayList<Double> mem = membership.get(index[kl]);
				int memlen = mem.size();
				int len = c.size();
				double e[] = new double[11];
				double weight = (min[kl]/sum);
				
				for (int a = 0; a < 11; a++)
					e[a] = predictors[i][a];
				clusters.get(index[kl]).add(len, e);
				membership.get(index[kl]).add(memlen,weight);
			}
		}

	}

	private double calculateEuclidean(int i, int j) {

		double euclideanDist = 0.0;
		for (int a = 1; a < attributes; a++) {
//			if(a==exclude1)// & a!=exclude2)
			euclideanDist += (predictors[i][a] - centroids[j][a]) * (predictors[i][a] - centroids[j][a]);
		}

		return Math.sqrt(euclideanDist);
	}

	private void recalculateCentroids() {

		Iterator<ArrayList<double[]>> aIter = clusters.iterator();
		Iterator<ArrayList<Double>> maIter = membership.iterator();
		int cl = 0;
		while (aIter.hasNext()) {

			ArrayList<double[]> a = (ArrayList<double[]>) aIter.next();
			ArrayList<Double> ma = (ArrayList<Double>) maIter.next();
			
			Iterator<double[]> dIter = a.iterator();
			Iterator<Double> mIter = ma.iterator();
			
			double[] avg = new double[attributes];
			int n = 0;
			double sum = 0.0;
			while (dIter.hasNext()) {

				double[] d = (double[]) dIter.next();
				double mem = (double) mIter.next();
				sum += mem;
				n++;

				for (int atribute = 1; atribute < attributes; atribute++) {
//					if(atribute==exclude1)// & atribute!=exclude2)
					avg[atribute] += (mem*d[atribute]);
				}
			}
			for (int atribute = 1; atribute < attributes; atribute++) {

				avg[atribute] /= sum;
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


	public static double[][] readData(int rows, String filename) {

		BufferedReader br = null;
		double[][] predictors = null;
		try {
			
			br = new BufferedReader(new FileReader(new File(filename)));
			predictors = new double[rows][];
			String r;
			int i = 0;
			while ((r = br.readLine()) != null) {

				predictors[i] = new double[11];

				String atr[] = r.split(",");
				int j = 0;

				for (String s : atr) {

					predictors[i][j] = Double.parseDouble(s);
					j++;
				}
				i++;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return predictors;
	}
	
	public void assignTestData(double [][]predictors){
		
		for (ArrayList<double[]> list : clusters) {
			list.clear();
		}

		for (int i = 0; i < predictors.length; i++) {

			double min = Double.POSITIVE_INFINITY;
			int index = 0;
			double euclideanDistance[] = new double[k];
			for (int j = 0; j < k; j++) {

				double euclideanDist = 0.0;
				for (int a = 1; a < attributes; a++) {
					euclideanDist += (predictors[i][a] - centroids[j][a]) * (predictors[i][a] - centroids[j][a]);
				}

				euclideanDistance[j] = Math.sqrt(euclideanDist);
				if (euclideanDistance[j] < min) {
					min = euclideanDistance[j];
					index = j;
				}
			}
			ArrayList<double[]> c = clusters.get(index);
			int l = c.size();
			double e[] = new double[11];
			for (int a = 0; a < 11; a++)
				e[a] = predictors[i][a];

			clusters.get(index).add(l, e);
		}

		
	}
	
	public static void main(String[] args) {
		String filename = "C:\\Users\\mpagnis\\Documents\\personal\\R\\uniq675.data";

		double[][] predictors = readData(675,filename);

		KLMeans km = new KLMeans(predictors, 4, 2, 10, 0.10);
		km.initializeCentroids();
		km.cluster();
		km.finalCluster();


		km.printClusters();
	}

	private void finalCluster() {
		for (ArrayList<double[]> list : clusters) {
			list.clear();
		}

		for (int i = 0; i < predictors.length; i++) {

			double min = Double.POSITIVE_INFINITY;
			int index = 0;
			double euclideanDistance[] = new double[k];
			for (int j = 0; j < k; j++) {

				euclideanDistance[j] = calculateEuclidean( i, j);
				if (euclideanDistance[j] < min) {
					min = euclideanDistance[j];
					index = j;
				}
			}
			ArrayList<double[]> c = clusters.get(index);
			int l = c.size();
			double e[] = new double[11];
			for (int a = 0; a < 11; a++)
				e[a] = predictors[i][a];

			clusters.get(index).add(l, e);
		}

	}

}
