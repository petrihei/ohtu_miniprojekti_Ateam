package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Blogi;
import tietokantaobjektit.Tag;

public class BlogiDAOTest {

    private Tietokanta db;
    private BlogiDAO dao;

    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new BlogiDAO(db);
    }

    @Test
    public void bloginVoiLisata() {
        Blogi lisattava = new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03");
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void bloginVoiHakea() {
        Blogi lisattava = new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03");
        long id = dao.lisaaVinkki(lisattava);
        Blogi lisatty = (Blogi) dao.haeVinkki(id);

        assertEquals("Ots", lisatty.getOtsikko());
        assertEquals("kuv", lisatty.getKuvaus());
        assertEquals("kirj", lisatty.getKirjoittaja());
        assertEquals("nimi", lisatty.getNimi());
        assertEquals("url", lisatty.getUrl());
        assertEquals("2017-12-03", lisatty.getPvm());
    }

    @Test
    public void bloginVoiLisataTagilla() {
        Blogi lisattava = new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03");
        lisattava.lisaaTag(new Tag("tagi"));
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void haetullaBlogillaOnTagit() {
        Blogi lisattava = new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03");
        lisattava.lisaaTag(new Tag("tagi1"));
        lisattava.lisaaTag(new Tag("tagi2"));

        long id = dao.lisaaVinkki(lisattava);
        Blogi lisatty = (Blogi) dao.haeVinkki(id);

        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
        assertEquals("tagi2", lisatty.getTagit().get(1).getTag());
    }

    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        BlogiDAO huonoDao = new BlogiDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        BlogiDAO huonoDao = new BlogiDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        BlogiDAO huonoDao = new BlogiDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Blogi("Ots", "kuv", "kirj", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        BlogiDAO huonoDao = new BlogiDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }

    @Test
    public void olemattomanBloginHakuPalauttaaNull() {
        assertEquals(null, dao.haeVinkki(-1l));
    }
}
