package tekstikayttis;


public class Validaattori implements Validoija {
    
    
    @Override
    public boolean validoiSyote(String validoitava, String kentanTyyppi) {
        boolean palautettava = true;
        palautettava = validoiPituus(validoitava, 200);
        if (kentanTyyppi.equals("Kirjailija")) {
            palautettava = validoiNimi(validoitava);
        }       
        if (kentanTyyppi.equals("ISBN")) { 
            palautettava = validoiISBN(validoitava);
        }
        if (kentanTyyppi.equals("otsikko*") || kentanTyyppi.equals("url*")) { 
            palautettava = validoiPakollinenKentta(validoitava);
        }
        return palautettava;
    }   
    
    // validoi noudattaako parametri ISBN-13 muotoa
    public boolean validoiISBN(String isbn) {
        boolean palautettava = false;
        String regex = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ])"
                + "{4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+"
                + "[- ]?[0-9]$";
        if (isbn.matches(regex)) {
            palautettava = true;
        }
        return palautettava;
    }
    
    // validoi, että nimi koostuu vain kirjaimista sekä väliviivasta
    //ja on yli yhden merkin pitkä. Saa sisältää välilyönnin.
    public boolean validoiNimi(String nimi) {
        boolean palautettava = false;
        String regex = "^[a-öA-Ö \\-]{2,}";
        if (nimi.matches(regex)) {
            palautettava = true;
        }
        return palautettava;
    }
    
    public boolean validoiPituus(String syote, int maksimiPituus) {
        boolean palautettava = false;
        String regex = "^.{0," + maksimiPituus + "}";
        if (syote.matches(regex)) {
            palautettava = true;
        }
        return palautettava;
    }
    
    public boolean validoiPakollinenKentta(String syote) {
        boolean palautettava = false;
        String tyhja = "";
        if (!syote.equals(tyhja)) {
            palautettava = true;
        }
        return palautettava;
    }
    
}