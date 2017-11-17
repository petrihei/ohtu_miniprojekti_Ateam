import tietokantaobjektit.Vinkki;
import dao.VinkkiDAO;
import dao.Tietokanta;
import java.sql.SQLException;
import tekstikayttis.*;

public class Main {
    public static void main(String[] args) {
        // Lisää vinkin tietokantaan. Suorita ja katso esim sqlite3:lla.
	    //Vinkki vinkki = new Vinkki("Testiotsikko", "Testikuvaus", "Kirja");
	    //Tietokanta db = new Tietokanta("jdbc:sqlite:TietokantaTest1.db");
	    //VinkkiDAO dao = new VinkkiDAO(db);
	    //dao.lisaaVinkki(vinkki);

    	Tietokanta db = new Tietokanta("jdbc:sqlite:TietokantaTest1.db");
        //Logiikka logiikka = new Logiikka(db);
	    IO io = new KonsoliIO();

        Tekstikayttis kali = new Tekstikayttis(io);
    	//Tekstikayttis kali = new Tekstikayttis(logiikka, io);
        kali.kayttoliittyma();
    }
}
