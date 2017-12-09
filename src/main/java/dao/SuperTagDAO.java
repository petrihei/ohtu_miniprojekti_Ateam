/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.SuperTag;

/**
 *
 * @author Chamion
 */
public class SuperTagDAO {
    private final Tietokanta db;
    private final String tyyppi;

    public SuperTagDAO(Tietokanta db, String tyyppi) {
        this.db = db;
        this.tyyppi = tyyppi;
    }
    
    public long lisaaTag(SuperTag lisattava) {
        String query = "INSERT INTO Tag (tag, tyyppi) values (?, ?)";
        long uusiId = -1;

        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lisattava.getTag());
            stmt.setString(2, tyyppi);
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
    
    protected SuperTag haeTag(long id) {
        SuperTag tag = null;
        String query = "SELECT * FROM Tag WHERE tag_id = ? AND tyyppi = ?";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            st.setString(2, tyyppi);
            ResultSet result = st.executeQuery();
            result.next();
            tag = new SuperTag(result.getString("tag"));
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }

        return tag;
    }
}
