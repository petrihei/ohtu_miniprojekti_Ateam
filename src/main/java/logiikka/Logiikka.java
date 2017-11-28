package logiikka;

import java.util.List;

import dao.*;
import tietokantaobjektit.*;

public class Logiikka {

    private Tietokanta db;
    private VinkkiDAO vinkkiDao;
    private KirjaDAO kirjaDao;

    public Logiikka(Tietokanta db) {
        this.db = db;
        this.vinkkiDao = new VinkkiDAO(db);
        this.kirjaDao = new KirjaDAO(db);
    }

    public boolean lisaaVinkki(Vinkki vinkki) {
        return vinkkiDao.lisaaVinkki(vinkki) != -1;
    }

    public List<Vinkki> kaikkiVinkit() {
        return vinkkiDao.kaikkiVinkit();
    }

    public Kirja lisaaKirja(Kirja kirja) {
        long id = kirjaDao.lisaaKirja(kirja);
        Kirja uusiKirja = kirjaDao.haeKirja(id);
        return uusiKirja;
    }

    public List<Vinkki> haeKaikkiVinkit() {
        return vinkkiDao.kaikkiVinkitJaTiedot();
    }

}
