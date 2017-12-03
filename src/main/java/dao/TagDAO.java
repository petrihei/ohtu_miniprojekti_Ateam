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
            ResultSet rs = stmt.getGeneratedKeys();
            uusiId = rs.getLong(1);
            
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            return -1;
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
            return -1;
        }

        return uusiId;
    }
    
    public Tag haeTag(long id) {
        Tag tag = null;
        String query = "SELECT * FROM Tag WHERE tag_id = ?";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            result.next();
            tag = new Tag(result.getString("tag"));
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }

        return tag;
    }
}
