package tekstikayttis;

import java.util.ArrayList;
import java.util.List;

import tietokantaobjektit.Vinkki;
import logiikka.Logiikka;
import tietokantaobjektit.Kirja;
import tietokantaobjektit.Tag;
import tietokantaobjektit.Video;
import tietokantaobjektit.Blogi;

/**
 * Created by hanna-leena on 17/11/17.
 */
public class Tekstikayttis {

    private Logiikka logiikka;
    private IO io;
    private Validoija validointi;

    public Tekstikayttis(Logiikka l, IO io) {
        this.logiikka = l;
        this.io = io;
        this.validointi = new Validaattori();
        io.print("***********************");
        io.print("*     Vinkkilista     *");
        io.print("***********************");
    }

    public void kayttoliittyma() {
        String valinta = this.tulostaToiminnallisuudet();
        while (!valinta.equals("0")) {

            if (valinta.equals("1")) {
                this.vinkinLisays();
            } else if (valinta.equals("2")) {
                this.vinkkienJaTietojenTulostus();
            } else {
                this.io.print("Virheellinen valinta");
            }
            valinta = this.tulostaToiminnallisuudet();
        }
        this.io.print("Kiitos vinkkilistan käytöstä!");
    }

    /*
    *  Vinkin lisääminen
     */
    public void vinkinLisays() {
        //kun lisaystoiminto valitaan, selvitetään ensin tyyppi
        //ja sen mukaan kysytään lisätietoja
        this.io.print("");
        this.io.print("Minkätyyppisen vinkin haluat lisätä? Valitse alta:");
        this.io.print("1: Kirja");
        this.io.print("2: Video");
        this.io.print("3: Blogi");
        this.io.print("0: Peruuta");
        String komento = this.io.nextLine();

        if (komento.equals("1")) {
            this.kirjanLisays();
        } else if (komento.equals("2")) {
            this.videonLisays();
        } else if(komento.equals("3")){
            this.bloginLisays();
        } else if (komento.equals("0")) {
            return;
        } else {
            this.io.print("Valitse toiminto listasta!");
            this.vinkinLisays();
        }
    }
    
    public void bloginLisays(){
        this.io.print("");
        String otsikko = kysyKentta("otsikko");
        String kuvaus = kysyKentta("kuvaus");
        String tekija = kysyKentta("tekijä");
        String nimi = kysyKentta("nimi");
        String url = kysyKentta("url");
        String pvm = kysyKentta("pvm");
        
        Blogi lisattava = new Blogi(otsikko, kuvaus, tekija, nimi, url, pvm);
        kysyTagit(lisattava);

        this.io.print("");
        
        lisaaVinkkiJaTulostaTiedot(lisattava);
    }

    public void videonLisays() {
        this.io.print("");
        String otsikko = kysyKentta("otsikko");
        String kuvaus = kysyKentta("kuvaus");
        String tekija = kysyKentta("tekijä");
        String url = kysyKentta("url");
        String pvm = kysyKentta("pvm");

        Video lisattava = new Video(otsikko, kuvaus, tekija, url, pvm);
        kysyTagit(lisattava);

        this.io.print("");
        
        lisaaVinkkiJaTulostaTiedot(lisattava);
    }

    public void kirjanLisays() {
        this.io.print("");
        String otsikko = kysyKentta("otsikko");
        String kuvaus = kysyKentta("kuvaus");

        Kirja lisattava = new Kirja(otsikko, kuvaus);
        kysyIsbn(lisattava);
        kysyKirjailija(lisattava);
        kysyTagit(lisattava);

        this.io.print("");

        lisaaVinkkiJaTulostaTiedot(lisattava);
    }
    
    private void lisaaVinkkiJaTulostaTiedot(Vinkki lisattava) {
        if (this.logiikka.lisaaVinkki(lisattava) != null) {
            this.io.print("Seuraavat tiedot tallennettu:");
            this.io.print(lisattava.toString());
        } else {
            this.io.print("Tallennus epäonnistui");
        }
    }

    /**
     * Tulostus
     */
    public String tulostaToiminnallisuudet() {
        this.io.print("");
        this.io.print("Valitse toiminnallisuus:");
        this.io.print("1: Lisää vinkkilistaan");
        this.io.print("2: Selaa vinkkejä");
        this.io.print("0: Poistu");
        this.io.print("");
        return this.io.nextLine();
    }

    public void vinkkienTulostus() {
        List<Vinkki> vinkit = logiikka.kaikkiVinkit();
        if (vinkit == null) {
            this.io.print("Ei vinkkejä. Valitse toiminto 1 lisätäksesi vinkin.");
        } else {
            this.io.print("Kaikki vinkit:");
            this.io.print("**************");
            for (Vinkki vinkki : vinkit) {
                this.io.print(vinkki.toString());
                this.io.print("*****  \n");
            }
        }
    }

    public void vinkkienJaTietojenTulostus() {
        List<Vinkki> vinkit = logiikka.haeKaikkiVinkit();
        if (vinkit == null) {
            this.io.print("Ei vinkkejä. Valitse toiminto 1 lisätäksesi vinkin.");
        } else {
            this.io.print("Kaikki vinkit:");
            this.io.print("**************");
            for (Vinkki vinkki : vinkit) {
                this.io.print(vinkki.toString() + "\n");
            }
        }

    }

    /**
     * Syötteen kysyminen
     */
    private String kysyKentta(String kentanTyyppi) {
        this.io.print("Anna vinkin " + kentanTyyppi + ":");
        return this.io.nextLine();
    }

    private void kysyTagit(Vinkki lisattava) {
        this.io.print("Anna lukuvinkin tagit. Erota eri tagit pilkulla:");
        String tagSyote = this.io.nextLine();
        List<Tag> tagit = this.tagienErottaminen(tagSyote);
        lisattava.setTagit(tagit);
    }

    private void kysyIsbn(Kirja lisattava) {
        this.io.print("Anna kirjan ISBN:");
        lisattava.setIsbn(kysyValidoitava("ISBN"));
    }

    private void kysyKirjailija(Kirja lisattava) {
        this.io.print("Anna kirjan kirjoittaja:");
        lisattava.setKirjailija(kysyValidoitava("Kirjailija"));
    }

    private String kysyValidoitava(String kentanTyyppi) {
        String validoitava = this.io.nextLine();
        String komento = "";
        while (!validoiSyote(validoitava, kentanTyyppi) || komento.equals("k")) {
            System.out.println(kentanTyyppi + " väärässä muodossa");
            System.out.println("Haluatko syöttää uuden? k/e");
            komento = this.io.nextLine();
            if (komento.equals("k")) {
                validoitava = this.io.nextLine();
                komento = "";
            } else {
                System.out.println(kentanTyyppi + " ei tallennettu");
                validoitava = "";
                break;
            }
        }
        return validoitava;
    }

    private boolean validoiSyote(String validoitava, String kentanTyyppi) {
        if (kentanTyyppi.equals("Kirjailija")) {
            return this.validointi.validoiNimi(validoitava);
        }
        if (kentanTyyppi.equals("ISBN")) {
            return this.validointi.validoiISBN(validoitava);
        }
        return false;
    }

    /**
     * Apumetodit
     */
    public List<Tag> tagienErottaminen(String syote) {
        List<Tag> tagLista = new ArrayList<>();
        String tagit[] = syote.split(",");
        for (int i = 0; i < tagit.length; i++) {
            String lisattava = tagit[i].trim();
            tagLista.add(new Tag(lisattava));
        }
        return tagLista;
    }
}
