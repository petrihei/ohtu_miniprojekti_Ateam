package tietokantaobjektit;

/**
 *
 * @author Chamion
 * Vastaparina tietokannan saman nimiselle taululle.
 */
public class Vinkki {
	
	// Vastaavat tietokantataulun sarakkeita.
	protected String otsikko;
	protected String kuvaus;
	protected String tyyppi;
	
	// Toistaiseksi ainut konstruktori. Ylikuormitus myöhemmin.
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

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.tyyppi + ": " + this.otsikko + "\n");
		sb.append("  Kuvaus: " + this.kuvaus);
		return sb.toString();
	}
}