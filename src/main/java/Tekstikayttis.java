import java.util.Scanner;

/**
 *
 * @author hanna-leena
 * Tässä luokassa on toiminnallisuudet tekstikäyttöliittymälle.
 * Käyttöliittymä kommunikoi sovelluslogiikan kanssa (as soon as it is done).
 */
public class Tekstikayttis {
    //private Logiikka logiikka;
    private Scanner lukija;
    
    /* "oikea" konstruktori
    public Tekstikayttis(Logiikka l){
        this.logiikka = l;
        this.lukija = new Scanner(System.in);
        System.out.println("***********************");
        System.out.println("*     Vinkkilista     *");
        System.out.println("***********************");
    }
    */
    
    //konstruktori testikäyttöön ennen kuin logiikka on valmis
    public Tekstikayttis(){
        this.lukija = new Scanner(System.in);
        System.out.println("***********************");
        System.out.println("*     Vinkkilista     *");
        System.out.println("***********************");
    }
    
    public void kayttoliittyma(){        
        String valinta = "x";
        while(!valinta.equals("0")){
            valinta = this.tulostaToiminnallisuudet();
            if(valinta.equals("1")){
                this.kirjanLisays();
            } else if(valinta.equals("0")){
                System.out.println("Kiitos vinkkilistan käytöstä!");
            } else {
                System.out.println("Virheellinen valinta");
            }
            
        }
    }
    
    public String tulostaToiminnallisuudet(){
        System.out.println("");
        System.out.println("Valitse toiminnallisuus:");
        System.out.println("1: Lisää vinkkilistaan");
        System.out.println("0: Poistu");
        System.out.println("");
        return this.lukija.nextLine();
    }
    
    public void kirjanLisays(){
        System.out.println("Anna kirjan otsikko:");
        String otsikko = this.lukija.nextLine();
        System.out.println("Anna kirjan kuvaus:");
        String kuvaus = this.lukija.nextLine();
        String[] tiedot = new String[2];
        tiedot[0] = otsikko;
        tiedot[1] = kuvaus;
        System.out.println("");
        /* kutsutaan sovelluslogiikka-luokan metodia tallenna (TBD!), joka
        palauttaa true, kun on lähettänyt tallennettavat tiedot eteenpäin
        if(this.logiikka.tallenna(tiedot)){
            System.out.println("Seuraavat tiedot tallennettu:");
            System.out.println("Otsikko: " + otsikko);
            System.out.println("Kuvaus: " + kuvaus);
        } else {
            System.out.println("Tallennus epäonnistui");
        }
        */
        //Alla oleva testikäyttöön, poistetaan kun if-lause saadaan toimimaan
        System.out.println("Seuraavat tiedot tallennettu:"); 
        System.out.println("Otsikko: " + otsikko);
        System.out.println("Kuvaus: " + kuvaus);
        return;
    }
}
