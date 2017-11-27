package tietokantaobjektit;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chamion Vastaparina tietokannan saman nimiselle taululle.
 */
public class Vinkki {

    // Vastaavat tietokantataulun sarakkeita.
    protected long id;
    protected String otsikko;
    protected String kuvaus;
    protected String tyyppi;
    protected List<Tag> tagit;

    // Toistaiseksi ainut konstruktori. Ylikuormitus myöhemmin.
    public Vinkki(String otsikko, String kuvaus, String tyyppi) {
        if (otsikko == null) {
            throw new IllegalArgumentException("Otsikko ei saa olla null.");
        }
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
        this.tyyppi = tyyppi;
        this.tagit = new ArrayList<>();
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTagit(List<Tag> tagit) {
        this.tagit = tagit;
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

    public List<Tag> getTagit() {
        if (this.tagit == null){
            System.out.println("ei tageja");
        }
        return tagit;
    }

    public long getId() {
        return id;
    }

    public void lisaaTag(Tag tag) {
        this.tagit.add(tag);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tyyppi + ": " + this.otsikko + "\n");
        sb.append("  Kuvaus: " + this.kuvaus);
        return sb.toString();
    }
}
