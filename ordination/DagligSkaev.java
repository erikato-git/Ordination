package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination {
	// Link til Dosis
	private ArrayList<Dosis> skaevDosis = new ArrayList<>();

	public DagligSkaev(LocalDate startDen, LocalDate slutDen) {
		super(startDen, slutDen);
	}

	public ArrayList<Dosis> getDoser() {
		return new ArrayList<>(this.skaevDosis);
	}

	public Dosis createDosis(LocalTime tid, double antal) {
		Dosis dosis = new Dosis(tid, antal);
		this.skaevDosis.add(dosis);
		return dosis;
	}

	public void removeDosis(Dosis dosis) {
		if (this.skaevDosis.contains(dosis)) {
			this.skaevDosis.remove(dosis);
		}
	}

	/**
	 * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
	 * @return
	 */
	@Override
	public double samletDosis() {
		double total = 0;
		for (Dosis d : this.skaevDosis) {
			total += d.getAntal();
		}
		return total * antalDage();

	}

	/**
	 * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig
	 * @return
	 */
	@Override
	public double doegnDosis() {
		return samletDosis() / antalDage();
	}

	/**
	 * Returnerer ordinationstypen som en String
	 * @return
	 */
	@Override
	public String getType() {
		return "Daglig Skæv";
	}

}
