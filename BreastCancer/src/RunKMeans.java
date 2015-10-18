import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunKMeans {

	//private static final Logger logger = Logger.getLogger(RunKMeans.class.getName());

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "C:\\Users\\mpagnis\\Documents\\personal\\R\\newclean.data";
		
		double [][]predictors = readData(filename);

		KMeans km = new KMeans(predictors, 2, 200, 0.10);
		km.initializeCentroids();
		km.cluster();
		
	}
	public static double[][] readData(String filename) {
		
		//"C:\\Users\\mpagnis\\Documents\\personal\\R\\newclean.data"
		BufferedReader br = null;
		double [][]predictors = null;
		try {
			
			br = new BufferedReader(new FileReader(new File(filename)));
			predictors = new double[675][];
			String r;
			int i = 0;
			while((r = br.readLine())!=null) {			
				//logger.log(Level.INFO, "\nline: "+r);
				
				predictors[i] = new double[10];
				
				String atr[] = r.split(",");
				int j = 0;
				
				for(String s: atr) {
					
						predictors[i][j] = Double.parseDouble(s);	
						j++;
				}
				i++;
								
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		
		return predictors;
	}

}
