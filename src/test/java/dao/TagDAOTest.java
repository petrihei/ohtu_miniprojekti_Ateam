package dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Tag;

public class TagDAOTest {
    
    private Tietokanta db;
    private TagDAO dao;
    
    @Before
    public void initTietokanta() {
        db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new TagDAO(db);
    }
    
    @Test
    public void taginVoiLisata() {
        Tag lisattava = new Tag("Tagi");
        long id = dao.lisaaTag(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void taginVoiHakea() {
        Tag lisattava = new Tag("Tagi");
        long id = dao.lisaaTag(lisattava);
        Tag lisatty = dao.haeTag(id);
        
        assertEquals("Tagi", lisatty.getTag());
    }
    
    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaTag(new Tag("tagii")));
    }
    
    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(null, huonoDao.haeTag(1));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaTag(new Tag("tagii")));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(null, huonoDao.haeTag(1));
    }
}
