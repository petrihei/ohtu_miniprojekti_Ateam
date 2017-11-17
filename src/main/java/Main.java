import tietokantaobjektit.Vinkki;
import dao.VinkkiDAO;
import dao.Tietokanta;
import java.sql.SQLException;
import logiikka.Logiikka;

public class Main {
    public static void main(String[] args) {
        Tietokanta db = new Tietokanta("jdbc:sqlite:TietokantaTest1.db");
        Logiikka logiikka = new Logiikka(db);

        // Lisää vinkin tietokantaan. Suorita ja katso esim sqlite3:lla.
        Vinkki vinkki = new Vinkki("Testiotsikko", "Testikuvaus", "Kirja");
        if (logiikka.lisaaVinkki(vinkki)) {
            System.out.println("Vinkki lisätty");
        }
    }
}
