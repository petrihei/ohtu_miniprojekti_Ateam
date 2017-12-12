package tietokantaobjektit;

import org.junit.Test;
import static org.junit.Assert.*;

public class PodcastTest {
    
    @Test
    public void toStringAntaaOikeanUlkoasun() {
        Podcast podcast = new Podcast("Ots", "kuv", "tek", "nimi", "url", "pvm");
        podcast.lisaaTag(new Tag("tagi"));
        podcast.lisaaTag(new Tag("toinen tagi"));
        podcast.lisaaRelatedCourse(new RelatedCourse("course"));
        podcast.lisaaRelatedCourse(new RelatedCourse("kurs"));
        String exp = "podcast-jakso: Ots\n  "
                + "Kuvaus: kuv\n  "
                + "Tekijä: tek\n  "
                + "Podcastin nimi: nimi\n  "
                + "Url: url\n  Pvm: pvm\n  "
                + "Tagit: tagi toinen tagi \n  "
                + "Liittyy kursseihin: course kurs ";
        assertEquals(exp, podcast.toString());
    }
}
