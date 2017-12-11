package tietokantaobjektit;

import java.util.ArrayList;
import java.util.List;

public class Blogi extends Vinkki {

    private String kirjoittaja;
    private String nimi;
    private String url;
    private String pvm;

    public Blogi(String otsikko, String kuvaus) {
        super(otsikko, kuvaus, "blogi");
    }

    public Blogi(String otsikko, String kuvaus, String kirjoittaja, String nimi, String url, String pvm) {
        super(otsikko, kuvaus, "blogi");
        this.kirjoittaja = kirjoittaja;
        this.nimi = nimi;
        this.url = url;
        this.pvm = pvm;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
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
    public List<String> tyypinTiedotJarjestyksessa() {
        ArrayList<String> tiedot = (ArrayList) new ArrayList();
        tiedot.add(this.kirjoittaja);
        tiedot.add(this.nimi);
        tiedot.add(this.url);
        tiedot.add(this.pvm);
        return tiedot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringPrefix(sb, "blogipostaus");
        sb.append("\n")
                .append("  Kirjoittaja: ").append(this.kirjoittaja).append("\n")
                .append("  Nimi: ").append(this.nimi).append("\n")
                .append("  Url: ").append(this.url).append("\n")
                .append("  Pvm: ").append(this.pvm).append("\n");
        toStringSuffix(sb);
        return sb.toString();
    }
}
