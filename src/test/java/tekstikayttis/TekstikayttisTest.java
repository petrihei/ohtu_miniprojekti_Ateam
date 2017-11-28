package tekstikayttis;

import dao.Tietokanta;
import java.util.List;
import logiikka.Logiikka;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import tietokantaobjektit.Tag;

/**
 *
 * @author petriheinonen
 */
public class TekstikayttisTest {

    private Tietokanta db;
    private Logiikka logiikka;

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

    @Test
    public void kirjanLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("1", "Marxin Pääoma", "paras", "08348696873", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma"));
        //assertEquals("Otsikko: Marxin Pääoma", io.outputs.get(7));
    }

    @Test
    public void kirjanLisayksenKuvausToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "08348696873", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: paras"));
        //assertEquals("Kuvaus: paras", io.outputs.get(8));
    }

    @Test
    public void kirjanLisayksenIsbnToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "08348696873", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN: 08348696873"));
    }

    @Test
    public void kirjanLisayksenKirjailijaToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "08348696873", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirjailija: Marx"));
    }

    @Test
    public void kirjanLisayksenTagitToimii() {
        String tagit = "tag, test";
        IOStub io = new IOStub("Marxin Pääoma", "paras", "08348696873", "Marx", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
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
        //List<String> vinkit = io.outputs;

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirja: testikirja\n  Kuvaus: teskikuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirja: Marxin Pääoma\n  Kuvaus: paras"));
        //assertEquals("Kaikki vinkit:", vinkit.get(3));
        //assertEquals("Kirja: testikirja\n  Kuvaus: teskikuvaus", vinkit.get(5));
        //assertEquals("Kirja: Marxin Pääoma\n  Kuvaus: paras", vinkit.get(7));
    }
    
    @Test
    public void vinkkienJaTietojenTulostusToimii() {
        IOStub io = new IOStub("2");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienJaTietojenTulostus();
        //List<String> vinkit = io.outputs;

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma\n  Kuvaus: paras"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN: 08348696873\n  Kirjailija: Marx"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tag test"));

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
