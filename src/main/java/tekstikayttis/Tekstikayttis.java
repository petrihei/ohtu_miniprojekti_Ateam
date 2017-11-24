package tekstikayttis;

import java.util.List;

import tietokantaobjektit.Vinkki;
import logiikka.Logiikka;

/**
 * Created by hanna-leena on 17/11/17.
 */
public class Tekstikayttis {
    private Logiikka logiikka;
    private IO io;
    
    public Tekstikayttis(Logiikka l, IO io){
        this.logiikka = l;
        this.io = io;
        io.print("***********************");
        io.print("*     Vinkkilista     *");
        io.print("***********************");
    }

    public void kayttoliittyma(){
        String valinta = "x";
        while(!valinta.equals("0")){
            valinta = this.tulostaToiminnallisuudet();
            if(valinta.equals("1")){
                this.kirjanLisays();
            } else if (valinta.equals("2")){
                this.vinkkienTulostus();
            } else if(valinta.equals("0")){
                System.out.println("Kiitos vinkkilistan käytöstä!");
            } else {
                System.out.println("Virheellinen valinta");
            }

        }
    }

    public String tulostaToiminnallisuudet(){
        this.io.print("");
        this.io.print("Valitse toiminnallisuus:");
        this.io.print("1: Lisää vinkkilistaan");
        this.io.print("2: Selaa vinkkejä");
        this.io.print("0: Poistu");
        this.io.print("");
        String s = this.io.nextLine();
        return s;
    }

    public void kirjanLisays(){
        this.io.print("Anna vinkin otsikko:");
        String otsikko = this.io.nextLine();
        this.io.print("Anna vinkin kuvaus:");
        String kuvaus = this.io.nextLine();
        Vinkki vinkki = new Vinkki(otsikko, kuvaus, "Kirja");

        this.io.print("");
        
        if(this.logiikka.lisaaVinkki(vinkki)){
            this.io.print("Seuraavat tiedot tallennettu:");
            this.io.print("Otsikko: " + otsikko);
            this.io.print("Kuvaus: " + kuvaus);
        } else {
            this.io.print("Tallennus epäonnistui");
        }
    }

    public void vinkkienTulostus(){
        List<Vinkki> vinkit = logiikka.kaikkiVinkit();
        if(vinkit == null){
            this.io.print("Ei vinkkejä. Valitse toiminto 1 lisätäksesi vinkin.");
        } else {
            this.io.print("Kaikki vinkit: ");
            this.io.print("**************");
            for (Vinkki vinkki : vinkit) {
                this.io.print(vinkki.toString());
                this.io.print("\n  *****  \n");
            }
        }

    }

}
