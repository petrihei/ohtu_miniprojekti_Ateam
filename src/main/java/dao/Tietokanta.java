package dao;

import java.sql.*;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

/**
 *
 * @author Chamion Luokka kaikkien DAOjen yhteiskäyttöön.
 */
public class Tietokanta {

    private String jdbcOsoite;

    /**
     * Parametri "jdbc:sqlite:TietokantaTest1.db" näyttää toimivan.
     *
     * @param jdbcAddress osoite, joka osoittaa käytettävään tietokantaan.
     */
    public Tietokanta(String jdbcOsoite) {
        this.jdbcOsoite = jdbcOsoite;
    }

    /**
     * Luo yhteyden tietokantaan sqlite driverin avulla.
     *
     * @return java.sql.Connection tyyppinen yhteys.
     */
    public Connection getConnection() {
        Connection conn;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.resetOpenMode(SQLiteOpenMode.CREATE); // Ei salli tietokannan luomista.
            config.enforceForeignKeys(true); // Foreign key ei saa osoittaa tyhjään
            conn = DriverManager.getConnection(this.jdbcOsoite, config.toProperties());
        } catch (SQLException e) {
            // Älähtää, jos jdbcAddress on huono.
            System.err.println("Yhteyden muodostus tietokantaan epäonnistui, " + e);
            return null;
        }
        return conn;
    }

    public void poistaKaikki() {
        // TODO
    }

    public void lisaaTestiData() {
        // TODO
    }
}
