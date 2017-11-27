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
        long id = dao.lisaaKirja(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void kirjanVoiHakea() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        long id = dao.lisaaKirja(lisattava);
        Kirja lisatty = dao.haeKirja(id);
        
        assertEquals("Ots", lisatty.getOtsikko());
        assertEquals("kuv", lisatty.getKuvaus());
        assertEquals("isb", lisatty.getIsbn());
        assertEquals("kirj", lisatty.getKirjailija());
    }
    
    @Test
    public void kirjanVoiLisataTagilla() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        lisattava.lisaaTag(new Tag("tagi"));
        long id = dao.lisaaKirja(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void haetullaKirjallaOnTag() {
        Kirja lisattava = new Kirja("Ots", "kuv", "isb", "kirj");
        lisattava.lisaaTag(new Tag("tagi1"));

        long id = dao.lisaaKirja(lisattava);
        Kirja lisatty = dao.haeKirja(id);
        
        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
    }
}
