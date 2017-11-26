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
}
