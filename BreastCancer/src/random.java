import java.util.Random;

public class random {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<5; i++){
			Random r1 = new Random();
			System.out.println(r1.nextInt(10));
		}
	}

}
