import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import java.util.logging.*;
/**
 * @author MPagnis
 *
 */
public class DataPreprocessor {
	
	private static final Logger logger = Logger.getLogger(DataPreprocessor.class.getName());
	
	public static List<Cancer> readData(String filename) {
		
		//"C:\\Users\\mpagnis\\Documents\\personal\\R\\breast-cancer-wisconsin.data"
		BufferedReader br = null;
		
		List<Cancer> rawData = new ArrayList<Cancer>();
		
		Cancer c = new Cancer();
		
		try {
			
			br = new BufferedReader(new FileReader(new File(filename,"r")));
			String r;
			
			while((r = br.readLine())!=null) {
				
				logger.log(Level.INFO, "\nline: "+r);
				
				List<Double> raw = new ArrayList<Double>();
				
				for(String s:r.split(",")) {
					
//					boolean m = s.matches("?");
//					m==true?pred.add(Double.NaN):pred.add(Double.parseDouble(s));
					
					if(s.matches("?"))
						raw.add(Double.NaN);
					else
						raw.add(Double.parseDouble(s));
					
				}
				
				logger.log(Level.INFO, "\nraw array after split: "+raw);
				
				c.setClassY(raw.remove(10));
				c.setSCN(raw.remove(0));
				c.setPredictors(raw);
				
				rawData.add(c);
				
				logger.log(Level.INFO, "\nCancer raw data object: "+c);
			}
			
			logger.log(Level.INFO, "\nList rawData: \n"+rawData);
			
			logger.log(Level.INFO, "\nCount rawData: \n"+rawData.size());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		
		return rawData;
	}
	
	public static List<Cancer> removeDuplicates(List<Cancer> rawData) {
		
		//got from R
		
		return null;
	}
	
	public static List<Cancer> cleanData(List<Cancer> uniqData) {
		
		//got from R
		return null;
	}
}
