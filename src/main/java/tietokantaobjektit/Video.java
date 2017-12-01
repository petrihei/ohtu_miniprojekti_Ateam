package tietokantaobjektit;

/**
 *
 * @author Chamion
 */
public class Video extends Vinkki  {
    
    private String tekija;
    private String url;
    private String pvm;
        
    public Video(String otsikko, String kuvaus, String tekija, String url, String pvm) {
        super(otsikko, kuvaus, "video");
        this.tekija = tekija;
        this.url = url;
        this.pvm = pvm;
    }
    
    public String getTekija() {
        return tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPvm() {
        return pvm;
    }

    public void setPvm(String pvm) {
        this.pvm = pvm;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tyyppi).append(": ").append(this.otsikko).append("\n")
                .append("  Kuvaus: ").append(this.kuvaus).append("\n")
                .append("  Tekijä: ").append(this.tekija).append("\n")
                .append("  Url: ").append(this.url).append("\n")
                .append("  Pvm: ").append(this.pvm).append("\n")
                .append("  Tagit: ");
        for (Tag tag : this.tagit){
            sb.append(tag.getTag()).append(" ");
        }
        return sb.toString();
    }
}
