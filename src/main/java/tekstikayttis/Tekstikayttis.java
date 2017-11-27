package tekstikayttis;

import java.util.List;

import tietokantaobjektit.Vinkki;
import logiikka.Logiikka;
import tietokantaobjektit.Tag;

/**
 * Created by hanna-leena on 17/11/17.
 */
public class Tekstikayttis {

    private Logiikka logiikka;
    private IO io;

    public Tekstikayttis(Logiikka l, IO io) {
        this.logiikka = l;
        this.io = io;
        io.print("***********************");
        io.print("*     Vinkkilista     *");
        io.print("***********************");
    }

    public void kayttoliittyma() {
        String valinta = this.tulostaToiminnallisuudet();
        while (!valinta.equals("0")) {

            if (valinta.equals("1")) {
                this.kirjanLisays();
                //this.vinkinLisays();
            } else if (valinta.equals("2")) {
                this.vinkkienTulostus();
                //this.vinkkienJaTietojenTulostus();
            } else {
                System.out.println("Virheellinen valinta");
            }
            valinta = this.tulostaToiminnallisuudet();
        }
        System.out.println("Kiitos vinkkilistan käytöstä!");
    }

    public String tulostaToiminnallisuudet() {
        this.io.print("");
        this.io.print("Valitse toiminnallisuus:");
        this.io.print("1: Lisää vinkkilistaan");
        this.io.print("2: Selaa vinkkejä");
        this.io.print("0: Poistu");
        this.io.print("");
        return this.io.nextLine();

    }

    public void vinkinLisays() {
        //kun lisaystoiminto valitaan, selvitetään ensin tyyppi
        //ja sen mukaan kysytään lisätietoja
    }

    public void kirjanLisays() {
        //tästä kirja-tyypin vinkin lisaava metodi
        //kutsuu lisaaKirja-metodia kirjaoliolle
        this.io.print("Anna vinkin otsikko:");
        String otsikko = this.io.nextLine();
        this.io.print("Anna vinkin kuvaus:");
        String kuvaus = this.io.nextLine();
        Vinkki vinkki = new Vinkki(otsikko, kuvaus, "kirja");

        this.io.print("");

        if (this.logiikka.lisaaVinkki(vinkki)) {
            this.io.print("Seuraavat tiedot tallennettu:");
            this.io.print("Otsikko: " + otsikko);
            this.io.print("Kuvaus: " + kuvaus);
        } else {
            this.io.print("Tallennus epäonnistui");
        }
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
                System.out.println(vinkki.toString());
                if (vinkki.getTagit() == null) {
                    System.out.println("ei tageja");
                } else {
                    for (Tag tag : vinkki.getTagit()) {
                        System.out.println(tag.getTag());
                    }
                }

            }
        }

    }

}
