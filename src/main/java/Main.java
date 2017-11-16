import tietokantaobjektit.Vinkki;
import dao.VinkkiDAO;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
		// Lisää vinkin tietokantaan. Suorita ja katso esim sqlite3:lla.
		Vinkki vinkki = new Vinkki("Testiotsikko", "Testikuvaus", "Kirja");
		new VinkkiDAO("jdbc:sqlite:TietokantaTest1.db").lisaaVinkki(vinkki);
    }
}
