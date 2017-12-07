package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                vinkit.add(new Vinkki(result.getLong("vinkki_id"),
                        result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("tyyppi")));
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
        
        String query = rakennaKaikkiTiedotQuery();
        
        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet result = stmt.executeQuery()) {
            while (result.next()) {
                String tyyppi = result.getString("tyyppi");
                
                Vinkki vinkki = parsiVinkkiResultista(result);
                
                if(vinkki == null) {
                    continue;
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
    
    // Apumetodi metodille kaikkiVinkitJaTiedot
    private Vinkki parsiVinkkiResultista(ResultSet result) throws SQLException {
        String tyyppi = result.getString("tyyppi");
        Vinkki vinkki;
        if (tyyppi.equals("kirja")) {
            vinkki = new Kirja(
                    result.getString("otsikko"),
                    result.getString("kuvaus"),
                    result.getString("kirja_ISBN"),
                    result.getString("kirja_kirjailija")
            );
        } else if (tyyppi.equals("video")) {
            vinkki = new Video(
                    result.getString("otsikko"),
                    result.getString("kuvaus"),
                    result.getString("video_tekija"),
                    result.getString("video_url"),
                    result.getString("video_pvm")
            );
        } else if (tyyppi.equals("blogi")) {
            vinkki = new Blogi(
                    result.getString("otsikko"),
                    result.getString("kuvaus"),
                    result.getString("blogi_kirjoittaja"),
                    result.getString("blogi_nimi"),
                    result.getString("blogi_url"),
                    result.getString("blogi_pvm")
            );
        } else if (tyyppi.equals("podcast")) {
            vinkki = new Podcast(
                    result.getString("otsikko"),
                    result.getString("kuvaus"),
                    result.getString("podcast_tekija"),
                    result.getString("podcast_nimi"),
                    result.getString("podcast_url"),
                    result.getString("podcast_pvm")
            );
        } else {
            System.err.println("Tunnistamaton vinkin tyyppi: " + tyyppi);
            return null;
        }
        if (result.getString("tagit") != null) {
            String[] tagitString = result.getString("tagit").split(",");
            for (String tagString : tagitString) {
                vinkki.lisaaTag(new Tag(tagString));
            }
        }
        return vinkki;
    }
    
    // Apumetodi metodille kaikkiVinkitJaTiedot
    private String rakennaKaikkiTiedotQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT Vinkki.otsikko, Vinkki.kuvaus, Vinkki.tyyppi");
        
        String[] kirjaSarakkeet = new String[]{"ISBN", "kirjailija"};
        String[] videoSarakkeet = new String[]{"tekija", "url", "pvm"};
        String[] blogiSarakkeet = new String[]{"kirjoittaja", "nimi", "url", "pvm"};
        String[] podcastSarakkeet = new String[]{"tekija", "nimi", "url", "pvm"};
        
        upotaSarakkeet("Kirja", kirjaSarakkeet, queryBuilder);
        upotaSarakkeet("Video", videoSarakkeet, queryBuilder);
        upotaSarakkeet("Blogi", blogiSarakkeet, queryBuilder);
        upotaSarakkeet("Podcast", podcastSarakkeet, queryBuilder);
        
        queryBuilder.append(", R.tagit ");
        queryBuilder.append("FROM Vinkki ");
        
        upotaLiitto("Kirja", queryBuilder);
        upotaLiitto("Video", queryBuilder);
        upotaLiitto("Blogi", queryBuilder);
        upotaLiitto("Podcast", queryBuilder);
        
        queryBuilder.append("LEFT OUTER JOIN ("
                + "SELECT GROUP_CONCAT(tag) AS tagit, vinkki FROM ("
                + "SELECT Tag.tag AS tag, VinkkiTag.vinkki AS vinkki FROM Tag, VinkkiTag "
                + "WHERE Tag.tag_id = VinkkiTag.tag ORDER BY VinkkiTag.vinkki"
                + ") GROUP BY vinkki"
                + ") AS R ON Vinkki.vinkki_id = R.vinkki;");
        
        return queryBuilder.toString();
    }
    
    // apumetodi apumetodille rakennaKaikkiTiedotQuery
    private void upotaSarakkeet(String taulunNimi, String[] sarakkeet, StringBuilder queryBuilder) {
        for(String sarake : sarakkeet) {
            queryBuilder.append(", ");
            queryBuilder.append(taulunNimi);
            queryBuilder.append(".");
            queryBuilder.append(sarake);
            queryBuilder.append(" AS ");
            queryBuilder.append(taulunNimi.toLowerCase());
            queryBuilder.append("_");
            queryBuilder.append(sarake);
        }
    }
    
    // apumetodi apumetodille rakennaKaikkiTiedotQuery
    private void upotaLiitto(String taulunNimi, StringBuilder queryBuilder) {
        queryBuilder.append("LEFT JOIN ");
        queryBuilder.append(taulunNimi);
        queryBuilder.append(" ON ");
        queryBuilder.append(taulunNimi);
        queryBuilder.append(".vinkki = Vinkki.vinkki_id ");
    }

    public boolean poistaVinkki(Vinkki poistettava) {
        Long poistettavaID = poistettava.getId();
        String query = "SELECT tag FROM VinkkiTag where vinkki = " + poistettavaID;
        try (Connection conn = this.db.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            List<Integer> Tag_ids = new ArrayList<Integer>();

            while (result.next()) {
                Tag_ids.add(result.getInt("tag"));

            }
            for (int tagID : Tag_ids) {
                query = "SELECT Count() from VinkkiTag WHERE tag = " + tagID;
                stmt = conn.prepareStatement(query);
                result = stmt.executeQuery();
                while (result.next()) {
                    int TagCounter = result.getInt("Count()");

                    if (TagCounter == 1) {
                        query = "Delete from VinkkiTag where vinkki = " + poistettavaID;
                        stmt = conn.prepareStatement(query);
                        stmt.executeUpdate();
                        query = "Delete from Tag where tag_id = " + tagID;
                        stmt = conn.prepareStatement(query);
                        stmt.executeUpdate();
                    } else {
                        query = "Delete from VinkkiTag where vinkki = " + poistettavaID;
                        stmt = conn.prepareStatement(query);
                        stmt.executeUpdate();
                    }
                }
            }
            String PoistettavanTyyppi = poistettava.getTyyppi();
            query = "Delete from " + PoistettavanTyyppi + " WHERE vinkki = " + poistettavaID + ";";
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
            query = "DELETE FROM Vinkki WHERE vinkki_id = " + poistettavaID;
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        }
        return false;
    }
}
