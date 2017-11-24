package logiikka;

import java.util.List;

import dao.Tietokanta;
import dao.VinkkiDAO;
import tietokantaobjektit.Vinkki;

public class Logiikka {
    
    private Tietokanta db;
    private VinkkiDAO vinkkiDao;
    
    public Logiikka(Tietokanta db) {
        this.db = db;
        this.vinkkiDao = new VinkkiDAO(db);
    }

    public boolean lisaaVinkki(Vinkki vinkki) {
        return vinkkiDao.lisaaVinkki(vinkki) != -1;
    }

    public List<Vinkki> kaikkiVinkit(){
        return vinkkiDao.kaikkiVinkit();
    }
}
