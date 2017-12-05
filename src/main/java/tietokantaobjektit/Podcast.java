package tietokantaobjektit;

public class Podcast extends Vinkki {

    private String tekija;
    private String nimi;
    private String url;
    private String pvm;

    public Podcast(String otsikko, String kuvaus) {
        super(otsikko, kuvaus, "podcast");
    }

    public Podcast(String otsikko, String kuvaus, String tekija, String nimi, String url, String pvm) {
        super(otsikko, kuvaus, "podcast");
        this.tekija = tekija;
        this.nimi = nimi;
        this.url = url;
        this.pvm = pvm;
    }

    public String getTekija() {
        return tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
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
        sb.append("podcast-jakso: ").append(this.otsikko).append("\n")
                .append("  Kuvaus: ").append(this.kuvaus).append("\n")
                .append("  Tekijä: ").append(this.tekija).append("\n")
                .append("  Podcastin nimi: ").append(this.nimi).append("\n")
                .append("  Url: ").append(this.url).append("\n")
                .append("  Pvm: ").append(this.pvm).append("\n")
                .append("  Tagit: ");
        for (Tag tag : this.tagit){
            sb.append(tag.getTag()).append(" ");
        }
        return sb.toString();
    }
}
