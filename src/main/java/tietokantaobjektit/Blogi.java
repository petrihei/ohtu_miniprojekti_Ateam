package tietokantaobjektit;

public class Blogi extends Vinkki {
    
    private String kirjoittaja;
    private String url;
    private String pvm;
    
    public Blogi(String otsikko, String kuvaus) {
        super(otsikko, kuvaus, "blogi");
    }

    public Blogi(String otsikko, String kuvaus, String kirjoittaja, String url, String pvm) {
        super(otsikko, kuvaus, "blogi");
        this.kirjoittaja = kirjoittaja;
        this.url = url;
        this.pvm = pvm;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
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
                .append("  Kirjoittaja: ").append(this.kirjoittaja).append("\n")
                .append("  Url: ").append(this.url).append("\n")
                .append("  Pvm: ").append(this.pvm).append("\n")
                .append("  Tagit: ");
        for (Tag tag : this.tagit){
            sb.append(tag.getTag()).append(" ");
        }
        return sb.toString();
    }
}
