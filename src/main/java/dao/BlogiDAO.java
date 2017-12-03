package dao;

import dao.Tietokanta;
import dao.VinkkiDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Blogi;
import tietokantaobjektit.Tag;

public class BlogiDAO {
    
    private Tietokanta db;

    public BlogiDAO(Tietokanta db) {
        this.db = db;
    }
    
    public Blogi haeBlogi(long id) {
        Blogi blogi = null;
        String query = "SELECT * FROM Vinkki"
                + " JOIN Blogi ON vinkki.vinkki_id = blogi.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ? ;";
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            
            if (result.next()) {
                blogi = new Blogi(result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("kirjoittaja"),
                        result.getString("url"),
                        result.getString("pvm"));
                do {
                    String tagString = result.getString("tag");
                    if(tagString != null) {
                        Tag tag = new Tag(tagString);
                        blogi.lisaaTag(tag);
                    }
                } while(result.next());
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }
        
        return blogi;
    }
    
    public long lisaaBlogi(Blogi lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);

        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Blogi ja yhdistetään Vinkkiin.
        String kirjaAddQuery = "INSERT INTO Blogi (vinkki, kirjoittaja, url, pvm) values (?, ?, ?, ?)";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(kirjaAddQuery)) {
            st.setLong(1, vinkkiId);
            st.setString(2, lisattava.getKirjoittaja());
            st.setString(3, lisattava.getUrl());
            st.setString(4, lisattava.getPvm());
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            //TBD Poista vinkki
            return -1;
        }
        lisattava.setId(vinkkiId);

        return vinkkiId;
    }
}
