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
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    vinkkiId = rs.getLong(1);
                    lisattava.setId(vinkkiId);
                }
            } catch (Exception e) {
            }

        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            return -1;
        }

        // Lisätään vinkin tagit.
//        TagDAO tagDao = new TagDAO(db);
//        for (Tag tag : lisattava.getTagit()) {
//            // Lisätään tag.
//            long tagId = tagDao.lisaaTag(tag);
//
//            // Liitetään tag Vinkkiin.
//            String vinkkiTagQuery = "INSERT INTO VinkkiTag (vinkki, tag) values (?, ?)";
//
//            try (Connection conn = this.db.getConnection();
//                    PreparedStatement st = conn.prepareStatement(vinkkiTagQuery)) {
//                st.setLong(1, vinkkiId);
//                st.setLong(2, tagId);
//                st.executeUpdate();
//
//            } catch (SQLException ex) {
//                System.out.println("SQL kysely epäonnistui: " + ex);
//                return -1;
//            }
//        }
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
                vinkit.add(new Vinkki(result.getString("otsikko"), result.getString("kuvaus"), result.getString("tyyppi")));
            }
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        }

        return vinkit;
    }

    public List<Vinkki> kaikkiVinkitJaTiedot() {
        //pitäisi palauttaa lista kaikista vinkeistä
        //sisältäen kaikki niiden tiedot
        KirjaDAO kirjaDao = new KirjaDAO(db);
        List<Vinkki> vinkit = kaikkiVinkit();
        List<Vinkki> kaikki = new ArrayList();
        for (Vinkki vinkki : vinkit) {
            if (vinkki.getTyyppi().equals("kirja")) {
                Kirja kirja = kirjaDao.haeKirja(vinkki.getId());
                if (kirja != null) {
                    kaikki.add(kirja);
                }
            }
        }

        return vinkit;
    }
}
