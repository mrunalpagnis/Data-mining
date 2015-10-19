import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class random {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
//		for(int i=0; i<5; i++){
//			Random r1 = new Random();
//			System.out.println(r1.nextInt(10));
//		}
//		
		File f  = new File("C:\\Users\\mpagnis\\Documents\\personal\\R\\newclean.data");
		BufferedReader br = null;
		double[][] predictors = null;
		try {
			String r;
			br = new BufferedReader(new FileReader(f));
			System.out.println(br.lines().count());
			while ((r = br.readLine()) != null) {			
				System.out.println(r);
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
		
		Scanner sc = new Scanner(new FileInputStream(f));

	}

}
