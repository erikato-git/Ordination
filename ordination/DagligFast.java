package ordination;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DagligFast extends Ordination {

	private Dosis[] doser = new Dosis[4];
	
	
	
	public DagligFast(LocalDate startDate, LocalDate slutDate) {
		super(startDate, slutDate);
	}
	
	@Override
	public double samletDosis() {
		double antal = 0;
		
		//Forudsætter at patienten får den samme dosis pr. dag
		for(int j = 0; j < doser.length ; j++) {
			antal += doser[j].getAntal();
		}
		
		return antal * antalDage();
	}

	@Override
	public double doegnDosis() {
		
		return samletDosis()/antalDage();
	}

	@Override
	public String getType() {
		return "Type: Dagligsfast";
	}
	
	
	//create dosis (komposition)
	public Dosis createDosis(LocalTime time, double antal) {
		Dosis d = new Dosis(time, antal);
		for(int i = 0 ; i < doser.length; i++) {
			if(doser[i] == null) {
				doser[i] = d;
				return d;
			}
		}
		return null;
	}
	
	public Dosis[] getDoser() {
		return doser;
	}
	
	
}
