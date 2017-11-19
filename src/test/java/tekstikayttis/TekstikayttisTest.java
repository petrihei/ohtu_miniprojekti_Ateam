/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekstikayttis;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petriheinonen
 */
public class TekstikayttisTest {
    
    @Test
    public void tulostaToiminnallisuudetToimii() {
        IOStub io = new IOStub("1");
        Tekstikayttis kayttis = new Tekstikayttis(io);
        kayttis.tulostaToiminnallisuudet();
        
        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }
    
    @Test
    public void kirjanLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras");
        Tekstikayttis kayttis = new Tekstikayttis(io);
        kayttis.kirjanLisays();
        
        assertEquals("Otsikko: Marxin Pääoma", io.outputs.get(7));
    }
    
    @Test
    public void kirjanLisayksenKuvausToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras");
        Tekstikayttis kayttis = new Tekstikayttis(io);
        kayttis.kirjanLisays();
        
        assertEquals("Kuvaus: paras", io.outputs.get(8));
    }
    
    @Test
    public void kayttoliittymaToimii() {
        IOStub io = new IOStub("0");
        Tekstikayttis kayttis = new Tekstikayttis(io);
        kayttis.kayttoliittyma();
        
        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }
    
}
    