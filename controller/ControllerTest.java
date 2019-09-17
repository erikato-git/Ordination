package controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import ordination.DagligFast;
import ordination.Dosis;
import ordination.Laegemiddel;
import ordination.Patient;

public class ControllerTest {

	@Test
	public void testOpretDagligFastOrdination() {

		Controller c = Controller.getController();
		Patient p = new Patient("xxxxxx-xxxx", "Ib", 65);
		Laegemiddel l = new Laegemiddel("Test", 0.1, 0.15, 0.16, "Styk");
		LocalDate startDen1 = LocalDate.of(2019, 9, 13);
		LocalDate slutDen1 = LocalDate.of(2019, 9, 13);

		//Test om det object der returneres har lægemidlet, der svarer til det indsatte lægemiddel
	
		DagligFast df1 = c.opretDagligFastOrdination(startDen1, slutDen1, p, l, 1, 2, 3, 4);
		
		assertEquals("Test", df1.getLaegemiddel().getNavn());
		

	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testForUgyldigDatoForDagligFast() {
		Controller c = Controller.getController();
		Patient p = new Patient("xxxxxx-xxxx", "Ib", 65);
		Laegemiddel l = new Laegemiddel("Test", 0.1, 0.15, 0.16, "Styk");

		LocalDate startDen2 = LocalDate.of(2019, 9, 13);
		LocalDate slutDen2 = LocalDate.of(2019, 9, 12);
		
		DagligFast df2 = c.opretDagligFastOrdination(startDen2, slutDen2, p, l, 1, 2, 3, 4);
		
	}

 	 
	@Test(expected = IllegalArgumentException.class)
	public void testForUgyldigAntalEnhederForDagligFast() {
		Controller c = Controller.getController();
		Patient p = new Patient("xxxxxx-xxxx", "Ib", 65);
		Laegemiddel l = new Laegemiddel("Test", 0.1, 0.15, 0.16, "Styk");
		LocalDate startDen1 = LocalDate.of(2019, 9, 13);
		LocalDate slutDen1 = LocalDate.of(2019, 9, 13);

		DagligFast df3 = c.opretDagligFastOrdination(startDen1, slutDen1, p, l, -1, 2, 3, 4);
		

	}
	

	@Test
	public void antalOrdinationerPrVægtPrLægemiddel() {

		//test for at den opretter antal ordination (fejl-fri)

		
		//test for ugyldig start-vægt og slut-vægt 

		
	}
	
}
