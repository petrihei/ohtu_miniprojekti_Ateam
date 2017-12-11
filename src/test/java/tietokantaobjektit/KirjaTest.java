package tietokantaobjektit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KirjaTest {

    @Test
    public void toStringAntaaOikeanUlkoasun() {
        Kirja kirja = new Kirja("Ots", "kuv", "978-3-16-148410-0", "kirj");
        kirja.lisaaTag(new Tag("tagi"));
        kirja.lisaaTag(new Tag("toinen tagi"));
        kirja.lisaaRelatedCourse(new RelatedCourse("course"));
        kirja.lisaaRelatedCourse(new RelatedCourse("kurs"));
        String exp = "kirja: Ots\n  "
                + "Kuvaus: kuv\n  "
                + "ISBN: 978-3-16-148410-0\n  "
                + "Kirjailija: kirj\n  "
                + "Tagit: tagi toinen tagi \n  "
                + "Liittyy kursseihin: course kurs ";
        assertEquals(exp, kirja.toString());
    }
}
