package ordination;

import java.time.LocalDate;
import java.util.ArrayList;

public class PN extends Ordination{

	private double antalEnheder;
    private ArrayList<LocalDate> doser = new ArrayList<>();

    
    public PN(LocalDate startDen, LocalDate slutDen, double antalEnheder) {
		super(startDen, slutDen);
		this.antalEnheder = antalEnheder;
	}
    
    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        if (givesDen.isBefore(getSlutDen())&& givesDen.isAfter(getStartDen())) {
        	this.doser.add(givesDen);
        	return true;
        }
        else return false;
        
    }

    public double doegnDosis() {
        return (getAntalGangeGivet()*getAntalEnheder())/ super.antalDage();
    }


    public double samletDosis() {
        return getAntalEnheder()*getAntalGangeGivet();
       
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        
        return doser.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

	@Override
	public String getType() {
		return "PN";
	}

}
