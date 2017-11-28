package tekstikayttis;

import java.util.ArrayList;
import java.util.List;

import tietokantaobjektit.Vinkki;
import logiikka.Logiikka;
import tietokantaobjektit.Kirja;
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
                //this.kirjanLisays();
                this.vinkinLisays();
            } else if (valinta.equals("2")) {
                this.vinkkienTulostus();
                //this.vinkkienJaTietojenTulostus();
            } else {
                this.io.print("Virheellinen valinta");
            }
            valinta = this.tulostaToiminnallisuudet();
        }
        this.io.print("Kiitos vinkkilistan käytöstä!");
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
        this.io.print("");
        this.io.print("Minkätyyppisen vinkin haluat lisätä? Valitse alta:");
        this.io.print("1: Kirja");
        this.io.print("0: Peruuta");
        String valinta = this.io.nextLine();
        if(valinta.equals("1")){
            this.kirjanLisays();
        } else if(valinta.equals("0")){
            return;
        } else {
            this.io.print("Valitse toiminto listasta!");
            this.vinkinLisays();
        }
        
    }

    public void kirjanLisays() {
        //tästä kirja-tyypin vinkin lisaava metodi
        //kutsuu lisaaKirja-metodia kirjaoliolle
        this.io.print("");
        this.io.print("Anna kirjan otsikko:");
        String otsikko = this.io.nextLine();
        this.io.print("Anna kirjan kuvaus:");
        String kuvaus = this.io.nextLine();
        this.io.print("Anna kirjan ISBN:");
        String isbn = this.io.nextLine();
        this.io.print("Anna kirjan kirjoittaja:");
        String kirjailija = this.io.nextLine();
        this.io.print("Anna lukuvinkin tagit. Erota eri tagit pilkulla:");
        String tagSyote = this.io.nextLine();
        List<Tag> tagit = this.tagienErottaminen(tagSyote);
        
        //Vinkki vinkki = new Vinkki(otsikko, kuvaus, "kirja");
        Kirja kirja = new Kirja(otsikko, kuvaus, isbn, kirjailija);
        kirja.setTagit(tagit);
        
        this.io.print("");

        if (this.logiikka.lisaaKirja(kirja)!=null) {
            this.io.print("Seuraavat tiedot tallennettu:");
            this.io.print("Otsikko: " + kirja.getOtsikko());
            this.io.print("Kuvaus: " + kirja.getKuvaus());
            this.io.print("ISBN: " + kirja.getIsbn());
            this.io.print("Kirjailija: " + kirja.getKirjailija());
            this.io.print("Tagit: ");
            for(Tag tag : kirja.getTagit()){
                this.io.print("  " + tag.getTag());
            }
        } else {
            this.io.print("Tallennus epäonnistui");
        }
    }
    
    public List<Tag> tagienErottaminen(String syote){
        List<Tag> tagLista = new ArrayList<>();
        String tagit[] = syote.split(",");
        for(int i = 0; i < tagit.length; i++){
            String lisattava = tagit[i].trim();
            tagLista.add(new Tag(lisattava));
        }
        return tagLista;
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
