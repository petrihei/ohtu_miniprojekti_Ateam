/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.RelatedCourse;

/**
 *
 * @author Chamion
 */
public class RelatedCourseDAOTest {
    
    private Tietokanta db;
    private RelatedCourseDAO dao;
    
    @Before
    public void initTietokanta() {
        db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new RelatedCourseDAO(db);
    }
    
    @Test
    public void relatedCoursenVoiLisata() {
        RelatedCourse lisattava = new RelatedCourse("Tagi");
        long id = dao.lisaaTag(lisattava);
        assertTrue(id != -1);
    }
    
    @Test
    public void relatedCoursenVoiHakea() {
        RelatedCourse lisattava = new RelatedCourse("Tagi");
        long id = dao.lisaaTag(lisattava);
        RelatedCourse lisatty = dao.haeTag(id);
        
        assertEquals("Tagi", lisatty.getTag());
    }
    
    @Test
    public void huonoTietokantaPalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaTag(new RelatedCourse("tagii")));
    }
    
    @Test
    public void huonoTietokantaPalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(null, huonoDao.haeTag(1));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNeg1Lisatessa() {
        Tietokanta huonoDB = new Tietokanta("");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(-1, huonoDao.lisaaTag(new RelatedCourse("tagii")));
    }
    
    @Test
    public void huonoTietokantaosoitePalauttaaNullHakiessa() {
        Tietokanta huonoDB = new Tietokanta("");
        TagDAO huonoDao = new TagDAO(huonoDB);
        assertEquals(null, huonoDao.haeTag(1));
    }
}
