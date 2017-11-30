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
import tietokantaobjektit.Tag;
import tietokantaobjektit.Video;

/**
 *
 * @author Chamion
 */
public class VideoDAO {
    private Tietokanta db;

    public VideoDAO(Tietokanta db) {
        this.db = db;
    }
    
    public Video haeVideo(long id) {
        Video video = null;
        String query = "SELECT * FROM Vinkki"
                + " JOIN Video ON vinkki.vinkki_id = Video.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ? ;";
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            
            if (result.next()) {
                video = new Video(result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("tekija"),
                        result.getString("url"),
                        result.getString("pvm"));
                do {
                    String tagString = result.getString("tag");
                    if(tagString != null) {
                        Tag tag = new Tag(tagString);
                        video.lisaaTag(tag);
                    }
                } while(result.next());
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        }
        
        return video;
    }
    
    public long lisaaVideo(Video lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);

        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Video ja yhdistetään Vinkkiin.
        String videoAddQuery = "INSERT INTO Video (vinkki, tekija, url, pvm) values (?, ?, ?, ?)";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(videoAddQuery)) {
            st.setLong(1, vinkkiId);
            st.setString(2, lisattava.getTekija());
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
