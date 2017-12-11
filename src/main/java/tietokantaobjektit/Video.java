package tietokantaobjektit;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chamion
 */
public class Video extends Vinkki {

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
    public List<String> tyypinTiedotJarjestyksessa() {
        ArrayList<String> tiedot = (ArrayList) new ArrayList();
        tiedot.add(this.tekija);
        tiedot.add(this.url);
        tiedot.add(this.pvm);
        return tiedot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringPrefix(sb);
        sb.append("\n")
                .append("  Tekijä: ").append(this.tekija).append("\n")
                .append("  Url: ").append(this.url).append("\n")
                .append("  Pvm: ").append(this.pvm).append("\n");
        toStringSuffix(sb);
        return sb.toString();
    }
}
