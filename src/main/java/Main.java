import tietokantaobjektit.Vinkki;
import dao.VinkkiDAO;
import dao.Tietokanta;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
		// Lisää vinkin tietokantaan. Suorita ja katso esim sqlite3:lla.
		Vinkki vinkki = new Vinkki("Testiotsikko", "Testikuvaus", "Kirja");
		Tietokanta db = new Tietokanta("jdbc:sqlite:TietokantaTest1.db");
		VinkkiDAO dao = new VinkkiDAO(db);
		dao.lisaaVinkki(vinkki);
    }
}
