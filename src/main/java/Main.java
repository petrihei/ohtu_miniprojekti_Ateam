import tietokantaobjektit.Vinkki;
import dao.VinkkiDAO;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello gradle!");
		Vinkki vinkki = new Vinkki("Testiotsikko", "Testikuvaus tähän", "Kirja");
		VinkkiDAO.lisaaVinkki(vinkki);
    }
}
