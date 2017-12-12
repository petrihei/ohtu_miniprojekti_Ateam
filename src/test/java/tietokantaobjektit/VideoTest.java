/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tietokantaobjektit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chamion
 */
public class VideoTest {

    @Test
    public void toStringAntaaOikeanUlkoasun() {
        Video video = new Video("Ots", "kuv", "tekija", "url", "pvm");
        video.lisaaTag(new Tag("tagi"));
        video.lisaaTag(new Tag("toinen tagi"));
        video.lisaaRelatedCourse(new RelatedCourse("course"));
        video.lisaaRelatedCourse(new RelatedCourse("kurs"));
        String exp = "video: Ots\n  "
                + "Kuvaus: kuv\n  "
                + "Tekijä: tekija\n  "
                + "Url: url\n  "
                + "Pvm: pvm\n  "
                + "Tagit: tagi toinen tagi \n  "
                + "Liittyy kursseihin: course kurs ";
        assertEquals(exp, video.toString());
    }
}
