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
    protected List<RelatedCourse> relatedCourses;

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
    
    public Vinkki(Long id,String otsikko, String kuvaus, String tyyppi){
        this(otsikko, kuvaus, tyyppi);
        this.id = id;
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
    
    public void setRelatedCourses(List<RelatedCourse> relatedCourses) {
        this.relatedCourses = relatedCourses;
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
    
    public List<RelatedCourse> getRealtedCourses() {
        if (this.tagit == null){
            System.out.println("vinkki ei liity yhteenkään kurssiin");
        }
        return relatedCourses;
    }

    public long getId() {
        return id;
    }

    public void lisaaTag(Tag tag) {
        this.tagit.add(tag);
    }
    
    public void lisaaRelatedCourse(RelatedCourse relatedCourse) {
        this.relatedCourses.add(relatedCourse);
    }
    
    public void lisaaSuperTag(String tag, String tyyppi) {
        if(tyyppi.equals(Tag.TYYPPI)) {
            lisaaTag(new Tag(tag));
        } else if(tyyppi.equals(RelatedCourse.TYYPPI)) {
            lisaaRelatedCourse(new RelatedCourse(tag));
        } else {
            System.err.println("Tuntematon supertag tyyppi");
        }
    }
    
    public List<String> tyypinTiedotJarjestyksessa() {
        ArrayList<String> tiedot = new ArrayList();
        tiedot.add(this.otsikko);
        tiedot.add(this.kuvaus);
        return tiedot;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringPrefix(sb);
        toStringSuffix(sb);
        return sb.toString();
    }
    
    protected void toStringPrefix(StringBuilder sb) {
        sb.append(this.tyyppi + ": " + this.otsikko + "\n");
        sb.append("  Kuvaus: " + this.kuvaus);
    }
    
    protected void toStringPrefix(StringBuilder sb, String tulostusTyyppi) {
        sb.append(tulostusTyyppi + ": " + this.otsikko + "\n");
        sb.append("  Kuvaus: " + this.kuvaus);
    }
    
    protected void toStringSuffix(StringBuilder sb) {
        liitaTagit(sb);
        //liitaRelatedCourses(sb);
    }
    
    private void liitaTagit(StringBuilder sb) {
        sb.append("  Tagit: ");
        for (Tag tag : this.tagit){
            sb.append(tag.getTag()).append(" ");
        }
    }
    
    private void liitaRelatedCourses(StringBuilder sb) {
        sb.append("  Liittyy kursseihin: ");
        for (RelatedCourse relatedCourse : this.relatedCourses){
            sb.append(relatedCourse.getTag()).append(" ");
        }
    }
}
