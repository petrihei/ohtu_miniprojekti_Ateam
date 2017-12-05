package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Kirja;
import tietokantaobjektit.Tag;

public class KirjaDAOTest {
    
    private Tietokanta db;
    private KirjaDAO dao;
    
    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new KirjaDAO(db);
    }
    
    @Test
    public void kirjanVoiLisata() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void kirjanVoiHakea() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        long id = dao.lisaaVinkki(lisattava);
        Kirja lisatty = (Kirja) dao.haeVinkki(id);
        
        assertEquals("Ots", lisatty.getOtsikko());
        assertEquals("kuv", lisatty.getKuvaus());
        assertEquals("isb", lisatty.getIsbn());
        assertEquals("kirj", lisatty.getKirjailija());
    }
    
    @Test
    public void kirjanVoiLisataTagilla() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        lisattava.lisaaTag(new Tag("tagi"));
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void haetullaKirjallaOnTagit() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        lisattava.lisaaTag(new Tag("tagi1"));
        lisattava.lisaaTag(new Tag("tagi2"));

        long id = dao.lisaaVinkki(lisattava);
        Kirja lisatty = (Kirja) dao.haeVinkki(id);
        
        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
        assertEquals("tagi2", lisatty.getTagit().get(1).getTag());
    }
    
    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        KirjaDAO huonoDao = new KirjaDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Kirja("Ots", "kuv", "isb", "kirj")));
    }
    
    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        KirjaDAO huonoDao = new KirjaDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        KirjaDAO huonoDao = new KirjaDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Kirja("Ots", "kuv", "isb", "kirj")));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        KirjaDAO huonoDao = new KirjaDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }
    
    @Test
    public void olemattomanKirjanHakuPalauttaaNull() {
        assertEquals(null, (Kirja) dao.haeVinkki(-1l));
    }
    
}
