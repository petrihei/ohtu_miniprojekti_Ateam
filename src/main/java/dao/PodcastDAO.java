package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Podcast;
import tietokantaobjektit.Tag;

public class PodcastDAO {
    
    private Tietokanta db;

    public PodcastDAO(Tietokanta db) {
        this.db = db;
    }
    
    public Podcast haePodcast(long id) {
        Podcast podcast = null;
        String query = "SELECT * FROM Vinkki"
                + " JOIN Podcast ON vinkki.vinkki_id = podcast.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ? ;";
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();

            if (result.next()) {
                podcast = new Podcast(result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("tekija"),
                        result.getString("nimi"),
                        result.getString("url"),
                        result.getString("pvm"));
                do {
                    String tagString = result.getString("tag");
                    if (tagString != null) {
                        Tag tag = new Tag(tagString);
                        podcast.lisaaTag(tag);
                    }
                } while (result.next());
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }

        return podcast;
    }

    public long lisaaPodcast(Podcast lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);

        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Podcast ja yhdistetään Vinkkiin.
        String podcastAddQuery = "INSERT INTO Podcast (vinkki, tekija, nimi, url, pvm) values (?, ?, ?, ?, ?)";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(podcastAddQuery)) {
            st.setLong(1, vinkkiId);
            st.setString(2, lisattava.getTekija());
            st.setString(3, lisattava.getNimi());
            st.setString(4, lisattava.getUrl());
            st.setString(5, lisattava.getPvm());
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
