package ordination;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

public class DagligFast extends Ordination {

	private Dosis[] doser = new Dosis[4];
	
	
	public DagligFast(LocalDate startDate, LocalDate slutDate) {
		super(startDate, slutDate);
	}
	
	@Override
	public double samletDosis() {
		double antal = 0;
		
		if(this.getStartDen().isBefore(getSlutDen()) || this.getStartDen().isEqual(getSlutDen())) {
			for(int j = 0; j < doser.length ; j++) {
				antal += doser[j].getAntal();
			}
			
			return antal * antalDage();
		}
		return -1;
	}
 
	@Override
	public double doegnDosis() {
		if(this.getStartDen().isBefore(getSlutDen())) {
			return samletDosis()/antalDage();
		}
		return -1;
	}

	@Override
	public String getType() {
		return "Type: Dagligfast";
	}
	 
	
	//create dosis (komposition)
	public Dosis createDosis(LocalTime time, double antal) {
		Dosis d = new Dosis(time, antal);
		if(antal < 0) {
			return null;
		}
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
