package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Kirja;
import tietokantaobjektit.Tag;

public class KirjaDAO {

    private Tietokanta db;

    public KirjaDAO(Tietokanta db) {
        this.db = db;
    }

    public Kirja haeKirja(long id) {
        Kirja kirja = null;
        String query = "SELECT * FROM Vinkki"
                + " JOIN Kirja ON vinkki.vinkki_id = kirja.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ? ;";
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(query)) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            
            if (result.next()) {
                kirja = new Kirja(result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("isbn"),
                        result.getString("kirjailija"));
                do {
                    String tagString = result.getString("tag");
                    if(tagString != null) {
                        Tag tag = new Tag(tagString);
                        kirja.lisaaTag(tag);
                    }
                } while(result.next());
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }
        
        return kirja;
    }

    public long lisaaKirja(Kirja lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);

        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Kirja ja yhdistetään Vinkkiin.
        String kirjaAddQuery = "INSERT INTO Kirja (vinkki, ISBN, kirjailija) values (?, ?, ?)";

        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(kirjaAddQuery)) {
            st.setLong(1, vinkkiId);
            st.setString(2, lisattava.getIsbn());
            st.setString(3, lisattava.getKirjailija());
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
