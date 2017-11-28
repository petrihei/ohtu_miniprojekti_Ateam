
package tekstikayttis;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class ValidaattoriTest {
    
    private Validaattori v;
    
    @Before
    public void initValidaattori() {
        this.v = new Validaattori();
    }

    //Testit ISBN-13 muodon validaattorille
    
    @Test
    public void validoiISBNAlkuisenOikein() {        
        assertTrue(v.validoiISBN("ISBN 978-0-596-52068-7"));
    }
    
    @Test
    public void validoiISBNValiviiva13KaksoispisteOikein() {
        assertTrue(v.validoiISBN("ISBN-13: 978-0-596-52068-7"));
    }
    
    @Test
    public void validoiPelkanNumeronOikein() {
        assertTrue(v.validoiISBN("9780596520687"));
    }
    
    @Test
    public void validoiPelkanNumeronValilyonneillaOikein() {
        assertTrue(v.validoiISBN("979 0 596 52068 7"));
    }
    
    @Test
    public void validoiJarjettomuudenVaaraksi() {
        assertFalse(v.validoiISBN("jfisj00"));
    }
    
    @Test
    public void validoiVaaraksiJosEiAla978Tai979() {
        assertFalse(v.validoiISBN("9750596520687"));
    }
    
    @Test
    public void validoiLiianLyhyenVaaraksi() {
        assertFalse(v.validoiISBN("979059652068"));
    }
    
    @Test
    public void validoiLiianPitkanVaaraksi() {
        assertFalse(v.validoiISBN("97905965206834"));
    }
    
    //Testit nimen pituuden ja aakkosista koostumisen validaattorille
    
    @Test
    public void validoiNimenOikein() {        
        assertTrue(v.validoiNimi("Minna"));
    }
    
    @Test
    public void validoiNimenValilyonnillaOikein() {        
        assertTrue(v.validoiNimi("Minna Möttönen"));
    }
    
    @Test
    public void validoiNimenValiviivallaOikein() {        
        assertTrue(v.validoiNimi("Minna-Mari"));
    }
    
    @Test
    public void validoiNimenErikoismerkeillaVaaraksi() {        
        assertFalse(v.validoiNimi("Minna@Mari!"));
    }
    
    @Test
    public void validoiYhdenKirjaimenNimenVaaraksi() {        
        assertFalse(v.validoiNimi("X"));
    }
}
