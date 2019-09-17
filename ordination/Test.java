package ordination;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import controller.Controller;
import storage.Storage;

public class Test {

	public static void main(String[] args) {

//		Laegemiddel l = new Laegemiddel("Test", 0.1, 0.15, 0.16, "Styk");
//		Laegemiddel l = new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
//		Laegemiddel l = new Laegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
		Laegemiddel l = new Laegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
		
		Controller c = Controller.getController();
		
//		assertEquals(3,c.antalOrdinationerPrVægtPrLægemiddel(80, 90, l));

		c.createSomeObjects();
		Storage s = c.getStorage();


		System.out.println(s.getAllPatienter());
		System.out.println(s.getAllLaegemidler());
		System.out.println(c.antalOrdinationerPrVægtPrLægemiddel(10, 100, l));
		
		
	}

}


