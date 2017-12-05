package dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        long id = dao.lisaaPodcast(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void podcastinVoiHakea() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        long id = dao.lisaaPodcast(lisattava);
        Podcast lisatty = dao.haePodcast(id);

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
        long id = dao.lisaaPodcast(lisattava);
        assertTrue(id != -1);
    }

    @Test
    public void haetullaPodcastllaOnTagit() {
        Podcast lisattava = new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03");
        lisattava.lisaaTag(new Tag("tagi1"));
        lisattava.lisaaTag(new Tag("tagi2"));

        long id = dao.lisaaPodcast(lisattava);
        Podcast lisatty = dao.haePodcast(id);

        assertEquals("tagi1", lisatty.getTagit().get(0).getTag());
        assertEquals("tagi2", lisatty.getTagit().get(1).getTag());
    }

    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaPodcast(new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(null, huonoDao.haePodcast(1));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaPodcast(new Podcast("Ots", "kuv", "tek", "nimi", "url", "2017-12-03")));
    }

    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        PodcastDAO huonoDao = new PodcastDAO(huonoDB);
        assertEquals(null, huonoDao.haePodcast(1));
    }

    @Test
    public void olemattomanPodcastnHakuPalauttaaNull() {
        assertEquals(null, dao.haePodcast(-1l));
    }
}
