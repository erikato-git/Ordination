package ordination;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import controller.Controller;

public class Test {

	public static void main(String[] args) {

		Controller c = Controller.getController();
		Patient p = new Patient("xxxxxx-xxxx", "Ib", 65);
		Laegemiddel l = new Laegemiddel("Test", 0.1, 0.15, 0.16, "Styk");
		LocalDate startDen = LocalDate.of(2019, 9, 13);
		LocalDate slutDen = LocalDate.of(2019, 9, 13);
		
		DagligFast test = c.opretDagligFastOrdination(startDen, slutDen, p, l, 1, 2, 3, 4);

		System.out.println(test);
		
		
	}

}
