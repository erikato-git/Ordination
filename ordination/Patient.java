package ordination;

import java.util.ArrayList;

public class Patient {
	private String cprnr;
	private String navn;
	private double vaegt;

	// link til Ordination
	private ArrayList<Ordination> ordinations = new ArrayList<>();

	public Patient(String cprnr, String navn, double vaegt) {
		this.cprnr = cprnr;
		this.navn = navn;
		this.vaegt = vaegt;
	}

	public String getCprnr() {
		return this.cprnr;
	}

	public String getNavn() {
		return this.navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public double getVaegt() {
		return this.vaegt;
	}

	public void setVaegt(double vaegt) {
		this.vaegt = vaegt;
	}

	// return new arraylist til ordination

	public ArrayList<Ordination> getOrdinationer() {
		return new ArrayList<>(this.ordinations);
	}

	// Add og Remove til Ordination Arraylist

	public void addOrdination(Ordination ordination) {
		if (!this.ordinations.contains(ordination)) {
			this.ordinations.add(ordination);
		}
	}

	public void removeOrdination(Ordination ordination) {
		if (this.ordinations.contains(ordination)) {
			this.ordinations.remove(ordination);
		}
	}

	@Override
	public String toString() {
		return this.navn + "  " + this.cprnr;
	}

}

