package ordination;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class DagligFastTest {
	
	
	@Test
	public void testSamletDosis() {
		
		//Objekter af dagligfast
		DagligFast df1 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 13));
		DagligFast df2 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));
		DagligFast df3 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 12));
				
		//opret 4 objekter af dosis ved at kalde createDosis() til df1, df2 og df3

		Dosis d1 = df1.createDosis(LocalTime.of(06, 00), 1);
		Dosis d2 = df1.createDosis(LocalTime.of(12, 00), 2);
		Dosis d3 = df1.createDosis(LocalTime.of(18, 00), 3);
		Dosis d4 = df1.createDosis(LocalTime.of(00, 00), 4);

		Dosis d5 = df2.createDosis(LocalTime.of(06, 00), 1);
		Dosis d6 = df2.createDosis(LocalTime.of(12, 00), 2);
		Dosis d7 = df2.createDosis(LocalTime.of(18, 00), 3);
		Dosis d8 = df2.createDosis(LocalTime.of(00, 00), 4);

		Dosis d9 = df3.createDosis(LocalTime.of(06, 00), 1);
		Dosis d10 = df3.createDosis(LocalTime.of(12, 00), 2);
		Dosis d11 = df3.createDosis(LocalTime.of(18, 00), 3);
		Dosis d12 = df3.createDosis(LocalTime.of(00, 00), 4);
		
		assertEquals(10,df1.samletDosis(),0.01);
		assertEquals(20,df2.samletDosis(),0.01);
		assertEquals(-1,df3.samletDosis(),0.01);
		
	}

	@Test
	public void testDoegnDosis() {

		DagligFast df1 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));
		DagligFast df2 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 12));
		
		Dosis d1 = df1.createDosis(LocalTime.of(06, 00), 1);
		Dosis d2 = df1.createDosis(LocalTime.of(12, 00), 2);
		Dosis d3 = df1.createDosis(LocalTime.of(18, 00), 3);
		Dosis d4 = df1.createDosis(LocalTime.of(00, 00), 4);

		Dosis d5 = df2.createDosis(LocalTime.of(06, 00), 1);
		Dosis d6 = df2.createDosis(LocalTime.of(12, 00), 2);
		Dosis d7 = df2.createDosis(LocalTime.of(18, 00), 3);
		Dosis d8 = df2.createDosis(LocalTime.of(00, 00), 4);
		
		assertEquals(10,df1.doegnDosis(),0.01);
		assertEquals(-1,df2.doegnDosis(),0.01);
		
	}
	
	
	@Test
	public void testGetType() {
		
		DagligFast df1 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));
		String message = df1.getType();
		assertEquals("Type: Dagligfast",message);
	}

	

	@Test
	public void testCreateDosis() {

		DagligFast df1 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));
		DagligFast df2 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));
		DagligFast df3 = new DagligFast(LocalDate.of(2019, 9, 13), LocalDate.of(2019, 9, 14));

		
		//test for doser[] tom
		
		Dosis d1 = df1.createDosis(LocalTime.of(00, 00), -1);
		Dosis d2 = df2.createDosis(LocalTime.of(00, 00), 0);
		Dosis dTest1 = new Dosis(LocalTime.of(00, 00), 0);

		//test for ugyldig antal
		assertEquals(null,d1);
		//test for at d2 bliver indsat på første plads i doser[]
		assertEquals(df2.getDoser()[0],d2);
		//test for at createDosis() returnerer objekt med rigtige attributter
		assertEquals(dTest1.getAntal(),d2.getAntal(),1); 
		assertEquals(dTest1.getTid(),d2.getTid()); 
		
		
		//test for doser[] fuld
		
		Dosis d5 = df3.createDosis(LocalTime.of(06, 00), 1);
		Dosis d6 = df3.createDosis(LocalTime.of(12, 00), 2);
		Dosis d7 = df3.createDosis(LocalTime.of(18, 00), 3);
		Dosis d8 = df3.createDosis(LocalTime.of(00, 00), 4);
		Dosis dTest2 = df3.createDosis(LocalTime.of(00, 00), 5);
		
		//test for at oprette object til fyldt array 
		assertEquals(null,dTest2);
		
		
	}

}
