/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class VinkkiDAOTest {

    @Test
    public void kaikkiVinkitJaTiedotHuonollaTietokannallaPalauttaaNull() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertEquals(null, huonoDao.kaikkiVinkitJaTiedot());
    }
    
}
