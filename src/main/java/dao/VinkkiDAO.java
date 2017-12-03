package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tietokantaobjektit.*;

/**
 *
 * @author Chamion DAO luokalle tietokantaobjektit.Vinkki
 */
public class VinkkiDAO {

    private Tietokanta db;

    /**
     * @param db Tietokanta, jota DAO tulee käyttämään.
     */
    public VinkkiDAO(Tietokanta db) {
        this.db = db;
    }

    /**
     * Lisää tietokantaan Vinkki-olioa vastaavan rivin Vinkki-tietokantatauluun.
     *
     * @param lisattava Vinkki-olio, joka lisätään tietokantaan.
     * @return long Palauttaa onnistuneen lisäyksen uuden ID:n tai
     * epäonnistuessa -1.
     */
    public long lisaaVinkki(Vinkki lisattava) {
        String query = "INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)";
        long vinkkiId = -1;

        // try-with-resource sulkee tarvittavat yhteydet try-osan jälkeen.
        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lisattava.getOtsikko());
            stmt.setString(2, lisattava.getKuvaus());
            stmt.setString(3, lisattava.getTyyppi());
            stmt.executeUpdate();

            // Hae uusi ID ja aseta se oliolle:
            ResultSet rs = stmt.getGeneratedKeys();
            vinkkiId = rs.getLong(1);
            lisattava.setId(vinkkiId);

        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            return -1;
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
            return -1;
        }
        
        if (!lisaaVinkkiTag(lisattava.getTagit(), vinkkiId)) {
            return -1;
        }

        return vinkkiId;
    }

    public boolean lisaaVinkkiTag(List<Tag> tagit, long vinkkiId) {
        TagDAO tagDao = new TagDAO(db);
        for (Tag tag : tagit) {
            // Lisätään tag.
            long tagId = tagDao.lisaaTag(tag);
            
            // Liitetään tag Vinkkiin.
            String vinkkiTagQuery = "INSERT INTO VinkkiTag (vinkki, tag) values (?, ?)";

            try (Connection conn = this.db.getConnection();
                    PreparedStatement st = conn.prepareStatement(vinkkiTagQuery)) {
                st.setLong(1, vinkkiId);
                st.setLong(2, tagId);
                st.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("SQL kysely epäonnistui: " + ex);
                return false;
            } catch (NullPointerException ex) {
                // Tietokanta-luokka tekee virheilmoituksen.
                return false;
            }
        }
        return true;
    }

    public List<Vinkki> kaikkiVinkit() {
        List<Vinkki> vinkit = new ArrayList();
        String query = "SELECT * FROM Vinkki";

        // try-with-resource sulkee suluissa määriteltyjen yhteydet try-osan jälkeen.
        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                vinkit.add(new Vinkki(result.getLong("vinkki_id"), result.getString("otsikko"), result.getString("kuvaus"), result.getString("tyyppi")));
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }

        return vinkit;
    }

    public List<Vinkki> kaikkiVinkitJaTiedot() {
        //pitäisi palauttaa lista kaikista vinkeistä
        //sisältäen kaikki niiden tiedot
        List<Vinkki> vinkit = new ArrayList<>();
        
        // Tähän lauseeseen ei toivottavasti tarvitse enää koskea ikinä.
        String query = "SELECT * FROM Vinkki "
                + "LEFT OUTER JOIN Kirja ON Vinkki.vinkki_id = Kirja.vinkki "
                + "LEFT OUTER JOIN Video ON Vinkki.vinkki_id = Video.vinkki "
                + "LEFT OUTER JOIN Blogi ON Vinkki.vinkki_id = Blogi.vinkki "
                + "LEFT OUTER JOIN Podcast ON Vinkki.vinkki_id = Podcast.vinkki "
                + "LEFT OUTER JOIN ("
                + "SELECT GROUP_CONCAT(tag) AS tagit, vinkki FROM ("
                + "SELECT Tag.tag AS tag, VinkkiTag.vinkki AS vinkki FROM Tag, VinkkiTag "
                + "WHERE Tag.tag_id = VinkkiTag.tag ORDER BY VinkkiTag.vinkki"
                + ") GROUP BY vinkki"
                + ") AS R ON Vinkki.vinkki_id = R.vinkki;";
        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet result = stmt.executeQuery()) {
            while (result.next()) {
                String tyyppi = result.getString("tyyppi");
                Vinkki vinkki;
                if(tyyppi.equals("kirja")) {
                    vinkki = new Kirja(
                            result.getString("otsikko"),
                            result.getString("kuvaus"),
                            result.getString("ISBN"),
                            result.getString("kirjailija")
                    );
                } else if(tyyppi.equals("video")) {
                    vinkki = new Video(
                            result.getString("otsikko"),
                            result.getString("kuvaus"),
                            result.getString("tekija"),
                            result.getString("url"),
                            result.getString("pvm")
                    );
                } else {
                    System.err.println("Tunnistamaton vinkin tyyppi: " + tyyppi);
                    continue;
                }
                if(result.getString("tagit") != null) {
                    String[] tagitString = result.getString("tagit").split(",");
                    for (String tagString : tagitString) {
                        vinkki.lisaaTag(new Tag(tagString));
                    }
                }
                vinkit.add(vinkki);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e);
            return null;
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
            return null;
        }
        
        return vinkit;
    }
}
