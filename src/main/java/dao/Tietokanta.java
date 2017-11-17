package dao;

import java.sql.*;

/**
 *
 * @author Chamion
 * Luokka kaikkien DAOjen yhteiskäyttöön.
 */
public class Tietokanta {
	private String jdbcOsoite;
    
    /**
     * Parametri "jdbc:sqlite:TietokantaTest1.db" näyttää toimivan.
     * @param jdbcAddress osoite, joka osoittaa käytettävään tietokantaan.
     */
    public Tietokanta(String jdbcOsoite) {
        this.jdbcOsoite = jdbcOsoite;
    }
    
    /**
     * Luo yhteyden tietokantaan sqlite driverin avulla.
     * @return java.sql.Connection tyyppinen yhteys.
     */
    public Connection getConnection(){
        Connection conn;
        try {
                conn = DriverManager.getConnection(this.jdbcOsoite);
        } catch(SQLException e) {
                // Älähtää, jos jdbcAddress on huono.
                System.err.println("Yhteyden muodostus tietokantaan epäonnistui, " + e);
                return null;
        }
        return conn;
    }
}