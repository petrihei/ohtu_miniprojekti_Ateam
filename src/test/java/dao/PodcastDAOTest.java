package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Podcast;
import tietokantaobjektit.Tag;

public class PodcastDAOTest {

    private Tietokanta db;
    private PodcastDAO dao;

    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new PodcastDAO(db);
    }

    @Test
    public void podcastinVoiLisata() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void podcastinVoiHakea() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        long id = dao.lisaaVinkki(lisattava);
        Podcast lisatty = (Podcast) dao.haeVinkki(id);

        assertEquals("Ots", lisatty.getOtsikko());
        assertEquals("kuv", lisatty.getKuvaus());
        assertEquals("tek", lisatty.getTekija());
        assertEquals("nimi", lisatty.getNimi());
        assertEquals("url", lisatty.getUrl());
        assertEquals("2017-12-03", lisatty.getPvm());
    }

    @Test
    public void podcastinVoiLisataTagilla() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        lisattava.lisaaTag(new Tag("tagi"));
        long id = dao.lisaaVinkki(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void haetullaPodcastllaOnTagit() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        lisattava.lisaaTag(new Tag("tagi1"));
        lisattava.lisaaTag(new Tag("tagi2"));

        long id = dao.lisaaVinkki(lisattava);
        Podcast lisatty = (Podcast) dao.haeVinkki(id);

        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
        assertEquals("tagi2", lisatty.getTagit().get(1).getTag());
    }

    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaVinkki(new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(null, huonoDao.haeVinkki(1));
    }

    @Test
    public void olemattomanPodcastnHakuPalauttaaNull() {
        assertEquals(null, dao.haeVinkki(-1l));
    }
}
