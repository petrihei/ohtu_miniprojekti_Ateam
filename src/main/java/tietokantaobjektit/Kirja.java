package tietokantaobjektit;

public class Kirja extends Vinkki {

    private String isbn;
    private String kirjailija;

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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tyyppi).append(": ").append(this.otsikko).append("\n")
                .append("  Kuvaus: ").append(this.kuvaus).append("\n")
                .append("  ISBN: ").append(this.isbn).append("\n")
                .append("  Kirjailija: ").append(this.kirjailija);
        return sb.toString();
    }
}
