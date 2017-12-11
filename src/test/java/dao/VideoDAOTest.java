package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Video;
import tietokantaobjektit.Tag;

/**
 *
 * @author Chamion
 */
public class VideoDAOTest {
    
    private Tietokanta db;
    private VideoDAO dao;
    
    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new VideoDAO(db);
    }
    
    @Test
    public void videonVoiLisata() {
        Video lisattava = new Video("Ots", "kuv", "tekija", "url", "pvm");
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void videonVoiHakea() {
        Video lisattava = new Video("Ots", "kuv", "tekija", "url", "pvm");
        long id = dao.lisaaVinkki(lisattava);
        Video lisatty = (Video) dao.haeVinkki(id);
        
        assertEquals("Ots", lisatty.getOtsikko());
        assertEquals("kuv", lisatty.getKuvaus());
        assertEquals("tekija", lisatty.getTekija());
        assertEquals("url", lisatty.getUrl());
        assertEquals("pvm", lisatty.getPvm());
    }
    
    @Test
    public void videonVoiLisataTagilla() {
        Video lisattava = new Video("Ots", "kuv", "tekija", "url", "pvm");
        lisattava.lisaaTag(new Tag("tagi"));
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void haetullaVideollaOnTagit() {
        Video lisattava = new Video("Ots", "kuv", "tekija", "url", "pvm");
        lisattava.lisaaTag(new Tag("tagi1"));
        lisattava.lisaaTag(new Tag("tagi2"));

        long id = dao.lisaaVinkki(lisattava);
        Video lisatty = (Video) dao.haeVinkki(id);
        
        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
        assertEquals("tagi2", lisatty.getTagit().get(1).getTag());
    }
    
    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VideoDAO huonoDao = new VideoDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Video("Ots", "kuv", "tekija", "url", "pvm")));
    }
    
    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VideoDAO huonoDao = new VideoDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }
    
    @Test
    public void eiVoiLisataNullUrlilla() {
        Video lisattava = new Video("Ots", "kuv", "tekija", null, "pvm");
        assertEquals(-1l, dao.lisaaVinkki(lisattava));
    }
    
    @Test
    public void olemattomanVideonHakuPalauttaaNull() {
        assertEquals(null, dao.haeVinkki(-1l));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        VideoDAO huonoDao = new VideoDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Video("Ots", "kuv", "tekija", "url", "pvm")));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        VideoDAO huonoDao = new VideoDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }
}
