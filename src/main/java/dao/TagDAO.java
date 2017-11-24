package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Tag;

public class TagDAO {

    private Tietokanta db;

    public TagDAO(Tietokanta db) {
        this.db = db;
    }
    
    public long lisaaTag(Tag lisattava) {
        String query = "INSERT INTO Tag (tag) values (?)";
        long uusiId = -1;

        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lisattava.getTag());
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
