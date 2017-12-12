package tietokantaobjektit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BlogiTest {
    
    @Test
    public void toStringAntaaOikeanUlkoasun() {
        Blogi blogi = new Blogi("Ots", "kuv", "kirj", "nimi", "url", "pvm");
        blogi.lisaaTag(new Tag("tagi"));
        blogi.lisaaTag(new Tag("toinen tagi"));
        blogi.lisaaRelatedCourse(new RelatedCourse("course"));
        blogi.lisaaRelatedCourse(new RelatedCourse("kurs"));
        String exp = "blogipostaus: Ots\n  "
                + "Kuvaus: kuv\n  "
                + "Kirjoittaja: kirj\n  "
                + "Nimi: nimi\n  "
                + "Url: url\n  "
                + "Pvm: pvm\n  "
                + "Tagit: tagi toinen tagi \n  "
                + "Liittyy kursseihin: course kurs ";
        assertEquals(exp, blogi.toString());
    }
}
