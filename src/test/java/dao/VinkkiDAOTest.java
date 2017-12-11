/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.*;

/**
 *
 * @author Chamion
 */
public class VinkkiDAOTest {
    
    private Tietokanta db;
    private VinkkiDAO dao;
    
    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new VinkkiDAO(db);
    }

    @Test
    public void kaikkiVinkitJaTiedotHuonollaTietokannallaPalauttaaNull() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertEquals(null, huonoDao.kaikkiVinkitJaTiedot());
    }
    
    @Test
    public void lisaaVinkkiTagHuonollaTietokannallaPalauttaaFalse() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag("doot"));
        List<SuperTag> tagit = new ArrayList<>();
        tagit.addAll(lisattava.getTagit());
        assertFalse(huonoDao.lisaaVinkkiTag(tagit, 1l, new SuperTagDAO(huonoDB,"tag")));
    }
    
    @Test
    public void kaikkiVinkitHuonollaTietokannallaPalauttaaTyhjanListan() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertTrue(huonoDao.kaikkiVinkit().isEmpty());
    }
    
    @Test
    public void lisaaVinkkiPalauttaaNeg1KunTagOnHuono() {
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag(null));
        assertEquals(-1l, dao.lisaaVinkki(lisattava));
    }
    
    @Test
    public void kaikkiVinkitJaTiedotHuonollaTietokantaosoitteellaPalauttaaNull() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertEquals(null, huonoDao.kaikkiVinkitJaTiedot());
    }
    
    @Test
    public void lisaaVinkkiTagHuonollaTietokantaosoitteellaPalauttaaFalse() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag("doot"));
        List<SuperTag> tagit = new ArrayList<>();
        tagit.addAll(lisattava.getTagit());
        assertFalse(huonoDao.lisaaVinkkiTag(tagit, 1l, new SuperTagDAO(huonoDB,"tag")));
    }
    
    @Test
    public void kaikkiVinkitHuonollaTietokantaosoitteellaPalauttaaTyhjanListan() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertTrue(huonoDao.kaikkiVinkit().isEmpty());
    }
}
