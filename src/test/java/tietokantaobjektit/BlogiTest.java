package tietokantaobjektit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BlogiTest {
    
    @Test
    public void toStringAntaaOikeanUlkoasun() {
        Blogi blogi = new Blogi("Ots", "kuv", "kirj", "url", "pvm");
        blogi.lisaaTag(new Tag("tagi"));
        blogi.lisaaTag(new Tag("toinen tagi"));
        String exp = "blogi: Ots\n  Kuvaus: kuv\n  Kirjoittaja: kirj\n  Url: url\n  Pvm: pvm\n  Tagit: tagi toinen tagi ";
        assertEquals(exp, blogi.toString());
    }
}
