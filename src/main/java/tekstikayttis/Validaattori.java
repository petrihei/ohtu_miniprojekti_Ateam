
package tekstikayttis;


public class Validaattori implements Validoija {
    
    // validoi noudattaako parametri ISBN-13 muotoa
    @Override
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
    @Override
    public boolean validoiNimi(String nimi) {
        boolean palautettava = false;
        String regex = "^[a-öA-Ö \\-]{2,}";
        if (nimi.matches(regex)) {
            palautettava = true;
        }
        return palautettava;
    }
    
}
