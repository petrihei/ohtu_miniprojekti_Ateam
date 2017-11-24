package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Kirja;

public class KirjaDAO {

    private Tietokanta db;

    public KirjaDAO(Tietokanta db) {
        this.db = db;
    }

    public long lisaaKirja(Kirja lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);
        
        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Kirja ja yhdistetään Vinkkiin.
        String query = "INSERT INTO Kirja (vinkki, ISBN, kirjailija) values (?, ?, ?)";
        long uusiId = -1;

        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, vinkkiId);
            stmt.setString(2, lisattava.getIsbn());
            stmt.setString(3, lisattava.getKirjailija());
            stmt.executeUpdate();
            
            // Hae uusi ID:
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    uusiId = rs.getLong(1);
                }
            } catch (Exception e) {};
            
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            return -1;
        }

        return uusiId;
    }
}
