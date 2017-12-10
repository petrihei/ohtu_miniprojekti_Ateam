package tietokantaobjektit;

import java.util.ArrayList;
import java.util.List;

public class Kirja extends Vinkki {

    private String isbn;
    private String kirjailija;

    public Kirja(String otsikko, String kuvaus) {
        super(otsikko, kuvaus, "kirja");
    }

    public Kirja(String otsikko, String kuvaus, String isbn, String kirjailija) {
        super(otsikko, kuvaus, "kirja");
        this.isbn = isbn;
        this.kirjailija = kirjailija;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getKirjailija() {
        return kirjailija;
    }

    public void setKirjailija(String kirjailija) {
        this.kirjailija = kirjailija;
    }

    @Override
    public List<String> tyypinTiedotJarjestyksessa() {
        ArrayList<String> tiedot = (ArrayList) new ArrayList();
        tiedot.add(this.isbn);
        tiedot.add(this.kirjailija);
        return tiedot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tyyppi).append(": ").append(this.otsikko).append("\n")
                .append("  Kuvaus: ").append(this.kuvaus).append("\n")
                .append("  ISBN: ").append(this.isbn).append("\n")
                .append("  Kirjailija: ").append(this.kirjailija).append("\n")
                .append("  Tagit: ");
        for (Tag tag : this.tagit) {
            sb.append(tag.getTag()).append(" ");
        }
        return sb.toString();
    }
}
