package tekstikayttis;

import dao.KirjaDAO;
import dao.Tietokanta;
import java.util.List;
import logiikka.Logiikka;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tietokantaobjektit.*;

/**
 *
 * @author petriheinonen
 */
public class TekstikayttisTest {

    private Tietokanta db;
    private Logiikka logiikka;

//    @BeforeClass
//    public void initTietokannanData() {
//        Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
//        
//        // TODO: Tyhjennä tietokanta ja populoi se testidatalla ennen testejä.
//        tietokanta.poistaKaikki();
//        tietokanta.lisaaTestiData();
//    }
    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.logiikka = new Logiikka(db);
    }

    @Test
    public void tulostaToiminnallisuudetToimii() {
        IOStub io = new IOStub("1");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.tulostaToiminnallisuudet();

        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }

    //Kirjan lisäys
    @Test
    public void kirjanLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("1", "Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma"));
    }

    @Test
    public void kirjanLisayksenKuvausToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: paras"));
    }

    @Test
    public void kirjanLisayksenIsbnToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN 978-0-596-52068-7"));
    }

    @Test
    public void kirjanLisayksenKirjailijaToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirjailija: Marx"));
    }

    @Test
    public void kirjanLisayksenTagitToimii() {
        String tagit = "tag, test";
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
    }

    //Videon lisäys
    @Test
    public void videonLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("2", "Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "video: Videon otsikko"));
    }

    @Test
    public void videonLisayksenKuvausToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: videon kuvaus"));
    }

    @Test
    public void videonLisayksenTekijaToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tekijä: videon tekijä"));
    }

    @Test
    public void videonLisayksenUrlToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.video.com/watch"));
    }

    @Test
    public void videonLisayksenPvmToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-01"));
    }

    @Test
    public void videonLisayksenTagitToimii() {
        String tagit = "tagi1, tagi2";
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "www.video.com/watch", "2017-12-01", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
    }

    @Test
    public void podcastinLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("4", "Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "podcast-jakso: Podcastin otsikko"));
    }

    @Test
    public void podcastinLisayksenKuvausToimii() {
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: podcastin kuvaus"));
    }

    @Test
    public void podcastinLisayksenTekijaToimii() {
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tekijä: podcastin tekijä"));
    }

    @Test
    public void podcastinLisayksenNimiToimii() {
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Podcastin nimi: podcastin nimi"));
    }

    @Test
    public void podcastinLisayksenUrlToimii() {
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.podcast.fm/listen"));
    }

    @Test
    public void podcastinLisayksenPvmToimii() {
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-05"));
    }

    @Test
    public void podcastinLisayksenTagitToimii() {
        String tagit = "tagi1, tagi2";
        IOStub io = new IOStub("Podcastin otsikko", "podcastin kuvaus", "podcastin tekijä", "podcastin nimi", "www.podcast.fm/listen", "2017-12-05", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.podcastinLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
    }

    // Blogin lisäys
    @Test
    public void bloginLisaysOikeillaTiedoillaToimii() {
        IOStub io = new IOStub("blogipostauksen otsikko", "blogipostauksen kuvaus",
                "blogipostauksen tekijä", "blogipostauksen nimi", "www.blogger.com/blog",
                "2017-12-01", "tag, test");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.bloginLisays();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "blogipostaus: blogipostauksen otsikko"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: blogipostauksen kuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirjoittaja: blogipostauksen tekijä"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Nimi: blogipostauksen nimi"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.blogger.com/blog"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-01"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tag test"));
    }

    @Test
    public void kayttoliittymaToimii() {
        IOStub io = new IOStub("0");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kayttoliittyma();

        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }

    @Test
    public void vinkkienSelausToimii() {
        IOStub io = new IOStub("2");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienTulostus();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: testikirja\n  Kuvaus: testikuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma\n  Kuvaus: paras"));

        assertTrue(arrayContainsSubstring(io.getOutputs(), "video: Videon otsikko\n  Kuvaus: videon kuvaus"));
    }

    @Test
    public void vinkkienJaTietojenTulostusToimii() {
        IOStub io = new IOStub("2");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienJaTietojenTulostus();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma\n  Kuvaus: paras"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN: ISBN 978-0-596-52068-7\n  Kirjailija: Marx"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tag test"));

        assertTrue(arrayContainsSubstring(io.getOutputs(), "video: Videon otsikko"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: videon kuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tekijä: videon tekijä"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.video.com/watch"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-01"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tagi1 tagi2"));

        assertTrue(arrayContainsSubstring(io.getOutputs(), "podcast-jakso: Podcastin otsikko"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: podcastin kuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tekijä: podcastin tekijä"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.podcast.fm/listen"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-05"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tagi1 tagi2"));

        assertTrue(arrayContainsSubstring(io.getOutputs(), "blogipostaus: blogipostauksen otsikko"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: blogipostauksen kuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirjoittaja: blogipostauksen tekijä"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Nimi: blogipostauksen nimi"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: www.blogger.com/blog"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-01"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tag test"));
    }

    @Test
    public void vinkkiPoistetaanKunOikeaId() {
        KirjaDAO kirjaDAO = new KirjaDAO(db);
        long vinkki = kirjaDAO.lisaaKirja(new Kirja("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx"));

        IOStub io = new IOStub(vinkki + "", "1");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienTulostusPoistamiseen();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), vinkki + " | kirja | Marxin Pääoma | paras"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Valitse vinkki, joka haluat poistaa (ID):"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Haluatko varmasti poistaa seuraavan vinkin?(1: Kyllä)"));

    }

    @Test
    public void vinkkiaEiPoistetaKunVaaraId() {
        IOStub io = new IOStub("k", "1");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienTulostusPoistamiseen();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "0: Peruuta poisto."));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Valitse vinkki, joka haluat poistaa (ID):"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Väärä ID"));
    }

    @Test
    public void poistonVoiPerua() {
        IOStub io = new IOStub("0");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienTulostusPoistamiseen();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "0: Peruuta poisto."));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Vinkin poisto peruttu"));
    }

    private boolean arrayContainsSubstring(List<String> list, String substr) {
        for (String str : list) {
            if (str.contains(substr)) {
                return true;
            }
        }
        return false;
    }
}
