/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekstikayttis;


import dao.Tietokanta;
import logiikka.Logiikka;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        IOStub io = new IOStub("Marxin Pääoma", "paras");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        
        assertEquals("Otsikko: Marxin Pääoma", io.outputs.get(7));
    }
    
    @Test
    public void kirjanLisayksenKuvausToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        
        assertEquals("Kuvaus: paras", io.outputs.get(8));
    }
    
    @Test
    public void kayttoliittymaToimii() {
        IOStub io = new IOStub("0");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kayttoliittyma();
        
        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }
    
}
    