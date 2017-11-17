package logiikka;

import dao.Tietokanta;
import dao.VinkkiDAO;
import tietokantaobjektit.Vinkki;

public class Logiikka {
    
    private Tietokanta db;
    
    public Logiikka(Tietokanta db) {
        this.db = db;
    }

    public boolean lisaaVinkki(Vinkki vinkki) {
        VinkkiDAO dao = new VinkkiDAO(db);
        return dao.lisaaVinkki(vinkki);
    }
}
