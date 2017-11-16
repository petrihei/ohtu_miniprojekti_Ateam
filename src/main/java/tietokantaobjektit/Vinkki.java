/**
 *
 * @author Chamion
 * Vastaparina tietokannan saman nimiselle taululle.
 */

public class Vinkki {
	
	private String otsikko;
	private String kuvaus;
	private String tyyppi;
	
	public Vinkki(String otsikko, String kuvaus, String tyyppi) {
		if(otsikko == null) {
			throw new IllegalArgumentException("Otsikko ei saa olla null.");
		}
		this.otsikko = otsikko;
		this.kuvaus = kuvaus;
		this.tyyppi = tyyppi;
	}
	
	public void setOtsikko(String otsikko) {
		this.otsikko = otsikko;
	}
	
	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}
	
	public void setTyyppi(String tyyppi) {
		this.tyyppi = tyyppi;
	}
	
	public String getOtsikko() {
		return this.otsikko;
	}
	
	public String getKuvaus() {
		return this.kuvaus;
	}
	
	public String getTyyppi() {
		return this.tyyppi;
	}
}