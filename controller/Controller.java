package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ordination.DagligFast;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.Ordination;
import ordination.PN;
import ordination.Patient;
import storage.Storage;

public class Controller {
	private Storage storage;
	private static Controller controller;

	private Controller() {
		this.storage = new Storage();
	}

	//tilføjet get-metode til at teste antalOrdinationerPrVægtPrLægemiddel i jUnit
	public Storage getStorage() {
		return storage;
	}
	
	public static Controller getController() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	public static Controller getTestController() {
		return new Controller();
	}

	/**
	 * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
	 * ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 *
	 * @return opretter og returnerer en PN ordination.
	 */
	public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen,
			Patient patient, Laegemiddel laegemiddel, double antal) {

		if (checkStartFoerSlut(startDen, slutDen) == true) {

			PN pn = new PN(startDen, slutDen, antal);
			pn.setLaegemiddel(laegemiddel);
			patient.addOrdination(pn);
			return pn;

		} else {
			throw new IllegalArgumentException("Ordinationen oprettes ikke ");
		}
	}

	/**
	 * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 */
	public DagligFast opretDagligFastOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			double morgenAntal, double middagAntal, double aftenAntal,
			double natAntal) {  

		if (checkStartFoerSlut(startDen, slutDen) && (morgenAntal >= 0) && (middagAntal >= 0) && (aftenAntal >= 0) && (natAntal >= 0) ) {

			DagligFast dagligFast = new DagligFast(startDen, slutDen);
			dagligFast.setLaegemiddel(laegemiddel);
			dagligFast.createDosis(LocalTime.of(6, 00), morgenAntal);
			dagligFast.createDosis(LocalTime.of(12, 00), middagAntal);
			dagligFast.createDosis(LocalTime.of(18, 00), aftenAntal);
			dagligFast.createDosis(LocalTime.of(00, 00), natAntal);

			patient.addOrdination(dagligFast);	
			return dagligFast;

		} else { 
			throw new IllegalArgumentException("Ordinationen oprettes ikke ");
		}
	}

	/**
	 * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
	 * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
	 *
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 */
	public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			LocalTime[] klokkeSlet, double[] antalEnheder) {

		if (checkStartFoerSlut(startDen, slutDen) == true) {
			DagligSkaev dagligSkaev = new DagligSkaev(startDen, slutDen);
			dagligSkaev.setLaegemiddel(laegemiddel);
			for (int i = 0; i < klokkeSlet.length; i++) {
				dagligSkaev.createDosis(klokkeSlet[i], antalEnheder[i]);
			}

			patient.addOrdination(dagligSkaev);

			return dagligSkaev;
		} else {
			throw new IllegalArgumentException("Ordinationen oprettes ikke ");
		}
	} 

	/**
	 * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
	 * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
	 * IllegalArgumentException
	 * Pre: ordination og dato er ikke null
	 */
	public void ordinationPNAnvendt(PN ordination, LocalDate dato) {

		if (dato.isBefore(ordination.getStartDen()) || dato.isAfter(ordination.getSlutDen())) {
			throw new IllegalArgumentException("Ordinationen oprettes ikke ");
		} else {
			ordination.givDosis(dato);
		}

	}

	/**
	 * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
	 * til patientens vægt). Det er en forskellig enheds faktor der skal
	 * anvendes, og den er afhængig af patientens vægt.
	 * Pre: patient og lægemiddel er ikke null
	 */
	public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
		double result;
		if (patient.getVaegt() < 25) {
			result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnLet();
		} else if (patient.getVaegt() > 120) {
			result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnTung();
		} else {
			result = patient.getVaegt()
					* laegemiddel.getEnhedPrKgPrDoegnNormal();
		}
		return result;
	}

	/**
	 * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
	 * ordinationer.
	 * Pre: laegemiddel er ikke null
	 */
	public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart,
			double vægtSlut, Laegemiddel laegemiddel) {

		int antal = 0;
		List<Patient> patienterIInterval = new ArrayList<>();

		//hent alle patienter fra det givne vægtinterval
		for (Patient p : this.storage.getAllPatienter()) {
			if (vægtStart <= p.getVaegt() && p.getVaegt() <= vægtSlut) {
				patienterIInterval.add(p);
			}
		}

		//for patienterne tjekkes om de ordinationer de har fået svarer til det givne laegemiddel 
		for (Patient p : patienterIInterval) {
			for (Ordination o : p.getOrdinationer()) {
				if (o.getLaegemiddel().equals(laegemiddel)) {
					antal++;
				}
			}
		}

		return antal;
	}

	public List<Patient> getAllPatienter() {
		return this.storage.getAllPatienter();
	}

	public List<Laegemiddel> getAllLaegemidler() {
		return this.storage.getAllLaegemidler();
	}

	/**
	 * Metode der kan bruges til at checke at en startDato ligger før en
	 * slutDato.
	 *
	 * @return true hvis startDato er før slutDato, false ellers.
	 */
	private boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
		boolean result = true;
		if (slutDato.compareTo(startDato) < 0) {
			result = false;
		}
		return result;
	}

	public Patient opretPatient(String cpr, String navn, double vaegt) {
		Patient p = new Patient(cpr, navn, vaegt);
		this.storage.addPatient(p);
		return p;
	}

	public Laegemiddel opretLaegemiddel(String navn,
			double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
			double enhedPrKgPrDoegnTung, String enhed) {
		Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
				enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
		this.storage.addLaegemiddel(lm);
		return lm;
	}

	public void createSomeObjects() {
		opretPatient("121256-0512", "Jane Jensen", 63.4);
		opretPatient("070985-1153", "Finn Madsen", 83.2);
		opretPatient("050972-1233", "Hans Jørgensen", 89.4);
		opretPatient("011064-1522", "Ulla Nielsen", 59.9);
		opretPatient("090149-2529", "Ib Hansen", 87.7);

		opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
		opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
		opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
		opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");

		opretPNOrdination(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 12),
				this.storage.getAllPatienter().get(0), this.storage.getAllLaegemidler()
						.get(1),
				123);

		opretPNOrdination(LocalDate.of(2019, 2, 12), LocalDate.of(2019, 2, 14),
				this.storage.getAllPatienter().get(0), this.storage.getAllLaegemidler()
						.get(0),
				3);

		opretPNOrdination(LocalDate.of(2019, 1, 20), LocalDate.of(2019, 1, 25),
				this.storage.getAllPatienter().get(3), this.storage.getAllLaegemidler()
						.get(2),
				5);

		opretPNOrdination(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 12),
				this.storage.getAllPatienter().get(0), this.storage.getAllLaegemidler()
						.get(1),
				123);

//Nedenstående kode har vi valgt at udkommenterer, da den var ugyldig pga. ugyldige enheder og derved
//forårsagede fejl i programmet ved opstart
		
//		opretDagligFastOrdination(LocalDate.of(2019, 1, 10),
//				LocalDate.of(2019, 1, 12), this.storage.getAllPatienter().get(1),
//				this.storage.getAllLaegemidler().get(1), 2, -1, 1, -1);
 
		LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40),
				LocalTime.of(16, 0), LocalTime.of(18, 45) };
		double[] an = { 0.5, 1, 2.5, 3 };

		opretDagligSkaevOrdination(LocalDate.of(2019, 1, 23),
				LocalDate.of(2019, 1, 24), this.storage.getAllPatienter().get(1),
				this.storage.getAllLaegemidler().get(2), kl, an);
	}

}
