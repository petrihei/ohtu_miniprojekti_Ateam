package vinkkikirjasto;


import dao.Tietokanta;
import tekstikayttis.*;
import logiikka.Logiikka;

public class Main {

    public static void main(String[] args) {
        Tietokanta db = new Tietokanta("jdbc:sqlite:tietokanta.db");
        Logiikka logiikka = new Logiikka(db);
        IO io = new KonsoliIO();
        Tekstikayttis kali = new Tekstikayttis(logiikka, io);
        kali.kayttoliittyma();
    }
}
