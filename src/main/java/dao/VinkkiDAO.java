package dao;

import java.sql.*;

import tietokantaobjektit.Vinkki;

/**
 *
 * @author Chamion
 * DAO luokalle tietokantaobjektit.Vinkki
 */
public class VinkkiDAO {
    
    private Tietokanta db;
    
    /**
     * @param db Tietokanta, jota DAO tulee käyttämään.
     */
    public VinkkiDAO(Tietokanta db) {
        this.db = db;
    }
    
    /**
     * Lisää tietokantaan Vinkki-olioa vastaavan rivin Vinkki-tietokantatauluun.
     * @param lisattava Vinkki-olio, joka lisätään tietokantaan.
     */
    public void lisaaVinkki(Vinkki lisattava) {
        // Luodaan yhteys tietokantaan.
        Connection conn = this.db.getConnection();
        if(conn == null) {
            return;
        }
        // Luodaan kysely.
        PreparedStatement stmt;
        try {
            //Kyselyn rakenne.
            stmt = conn.prepareStatement("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)");
        } catch(SQLException e){
            System.err.println("SQL kyselyn muodostus epäonnistui, " + e);
            return;
        }
        try {
            //Syötetään Vinkin attribuutit kyselyyn.
            stmt.setString(1, lisattava.getOtsikko());
            stmt.setString(2, lisattava.getKuvaus());
            stmt.setString(3, lisattava.getTyyppi());
        } catch(SQLException e){
            System.err.println("SQL kyselyn arvojen sijoitus epäonnistui, " + e);
            return;
        }
        try {
            //Suoritetaan kysely.
            stmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("SQL kyselyn suorittaminen epäonnistui, " + e);
        }
    }
}