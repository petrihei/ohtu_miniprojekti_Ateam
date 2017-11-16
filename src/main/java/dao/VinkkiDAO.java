package dao;

import java.sql.*;

import tietokantaobjektit.Vinkki;

/**
 *
 * @author Chamion
 * DAO luokalle tietokantaobjektit.Vinkki
 */
public class VinkkiDAO {
    
    private String jdbcAddress;
    
    /**
     * Parametri "jdbc:sqlite:TietokantaTest1.db" näyttää toimivan.
     * @param jdbcAddress osoite, jota DAO käyttää.
     */
    public VinkkiDAO(String jdbcAddress) {
        this.jdbcAddress = jdbcAddress;
    }
    
    /**
     * Luo yhteyden tietokantaan sqlite driverin avulla.
     * @return java.sql.Connection tyyppinen yhteys.
     */
    private Connection getConnection(){
        Connection conn;
        try {
                conn = DriverManager.getConnection(this.jdbcAddress);
        } catch(SQLException e) {
                // Älähtää, jos jdbcAddress on huono.
                System.err.println("Yhteyden muodostus tietokantaan epäonnistui, " + e);
                return null;
        }
        return conn;
    }
    
    /**
     * Lisää tietokantaan Vinkki-olioa vastaavan rivin Vinkki-tietokantatauluun.
     * @param lisattava 
     */
    public void lisaaVinkki(Vinkki lisattava) {
        // Luodaan yhteys tietokantaan.
        Connection conn = getConnection();
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