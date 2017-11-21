package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public boolean lisaaVinkki(Vinkki lisattava) {
        // Luodaan yhteys tietokantaan.
        Connection conn = this.db.getConnection();
        if(conn == null) {
            return false;
        }
        // Luodaan kysely.
        PreparedStatement stmt;
        try {
            //Kyselyn rakenne.
            stmt = conn.prepareStatement("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)");
        } catch(SQLException e){
            System.err.println("SQL kyselyn muodostus epäonnistui, " + e);
            return false;
        }
        try {
            //Syötetään Vinkin attribuutit kyselyyn.
            stmt.setString(1, lisattava.getOtsikko());
            stmt.setString(2, lisattava.getKuvaus());
            stmt.setString(3, lisattava.getTyyppi());
        } catch(SQLException e){
            System.err.println("SQL kyselyn arvojen sijoitus epäonnistui, " + e);
            return false;
        }
        try {
            //Suoritetaan kysely.
            stmt.executeUpdate();
        } catch(SQLException e){
            System.err.println("SQL kyselyn suorittaminen epäonnistui, " + e);
            return false;
        }
        return true;
    }
    
    public List<Vinkki> kaikkiVinkit() {
        List<Vinkki> vinkit = new ArrayList();
        String query = "SELECT * FROM Vinkki";
        
        // try-with-resource sulkee tarvittavat yhteydet try-osan jälkeen.
        try (Connection conn = this.db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery()) {
            
            while (result.next()) {
                vinkit.add(new Vinkki(result.getString("otsikko"), result.getString("kuvaus"), result.getString("tyyppi")));
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui, " + ex);
        }

        return vinkit;
    }
}
