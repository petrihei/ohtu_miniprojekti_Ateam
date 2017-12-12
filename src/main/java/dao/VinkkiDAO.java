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

        List<SuperTag> tagit = new ArrayList<>();
        tagit.addAll(lisattava.getTagit());
        List<SuperTag> kurssit = new ArrayList<>();
        kurssit.addAll(lisattava.getRelatedCourses());

        if (!lisaaVinkkiTag(tagit, vinkkiId, new SuperTagDAO(db, "tag"))) {
            return -1;
        }

        if (!lisaaVinkkiTag(kurssit, vinkkiId, new SuperTagDAO(db, "related_course"))) {
            return -1;
        }

        return vinkkiId;
    }

    public boolean lisaaVinkkiTag(List<SuperTag> tagit, long vinkkiId, SuperTagDAO dao) {
        for (SuperTag tag : tagit) {
            // Lisätään tag.

            long tagId = dao.lisaaTag(tag);

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

        //Tähän kaikki halutut taginkaltaiset. Katso mallia aiemmista.
        String[] tagTyypit = new String[]{TagDAO.TYYPPI, RelatedCourseDAO.TYYPPI};

        String query = rakennaKaikkiTiedotQuery(tagTyypit);

        try (Connection conn = this.db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet result = stmt.executeQuery()) {
            while (result.next()) {

                Vinkki vinkki = parsiVinkkiResultista(result, tagTyypit);

                if (vinkki == null) {
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
    private Vinkki parsiVinkkiResultista(ResultSet result, String[] tagTyypit) throws SQLException {
        String tyyppi = result.getString("tyyppi");
        Vinkki vinkki;
        //Parsitaan vinkki sen tyypin mukaan.
        switch (tyyppi) {
            case "kirja":
                vinkki = new Kirja(
                        result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("kirja_ISBN"),
                        result.getString("kirja_kirjailija")
                );
                break;
            case "video":
                vinkki = new Video(
                        result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("video_tekija"),
                        result.getString("video_url"),
                        result.getString("video_pvm")
                );
                break;
            case "blogi":
                vinkki = new Blogi(
                        result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("blogi_kirjoittaja"),
                        result.getString("blogi_nimi"),
                        result.getString("blogi_url"),
                        result.getString("blogi_pvm")
                );
                break;
            case "podcast":
                vinkki = new Podcast(
                        result.getString("otsikko"),
                        result.getString("kuvaus"),
                        result.getString("podcast_tekija"),
                        result.getString("podcast_nimi"),
                        result.getString("podcast_url"),
                        result.getString("podcast_pvm")
                );
                break;
            default:
                System.err.println("Tunnistamaton vinkin tyyppi: " + tyyppi);
                return null;
        }
        //Parsii kaikki taginkaltaiset automaattisesti tagTyypit taulukon ja Vinkki-luokan toimintojen avulla.
        for (String tagTyyppi : tagTyypit) {
            String sarake = tagTyyppi + "_concat";
            if (result.getString(sarake) != null) {
                String[] tagitString = result.getString(sarake).split(",");
                for (String tagString : tagitString) {
                    vinkki.lisaaSuperTag(tagString, tagTyyppi);
                }
            }
        }
        return vinkki;
    }

    // Apumetodi metodille kaikkiVinkitJaTiedot
    private String rakennaKaikkiTiedotQuery(String[] tagTyypit) {
        StringBuilder queryBuilder = new StringBuilder();
        // Vinkin sarakkeet
        queryBuilder.append("SELECT Vinkki.otsikko, Vinkki.kuvaus, Vinkki.tyyppi");

        // Lisätään jokaisen tyyppien tarvitsemat sarakkeet erikseen.
        String[] kirjaSarakkeet = new String[]{"ISBN", "kirjailija"};
        String[] videoSarakkeet = new String[]{"tekija", "url", "pvm"};
        String[] blogiSarakkeet = new String[]{"kirjoittaja", "nimi", "url", "pvm"};
        String[] podcastSarakkeet = new String[]{"tekija", "nimi", "url", "pvm"};
        upotaSarakkeet("Kirja", kirjaSarakkeet, queryBuilder);
        upotaSarakkeet("Video", videoSarakkeet, queryBuilder);
        upotaSarakkeet("Blogi", blogiSarakkeet, queryBuilder);
        upotaSarakkeet("Podcast", podcastSarakkeet, queryBuilder);

        // Taginkaltaisille sarakkeet muodostetaan automaattisesti tagTyypit taulukon avulla.
        upotaTagSarakkeet(tagTyypit, queryBuilder);
        queryBuilder.append(" FROM Vinkki ");

        // Lisätään tyyppien tarvitsemat JOIN:it
        upotaLiitto("Kirja", queryBuilder);
        upotaLiitto("Video", queryBuilder);
        upotaLiitto("Blogi", queryBuilder);
        upotaLiitto("Podcast", queryBuilder);

        // Taginkaltaisten liitot automaattisesti tagTyypit taulukon avulla.
        upotaTagLiitot(tagTyypit, queryBuilder);
        queryBuilder.append(";");
        return queryBuilder.toString();
    }

    private void upotaTagSarakkeet(String[] tagTyypit, StringBuilder queryBuilder) {
        // Saadaan tyyppi_concat sarakkeeseen pilkuilla erotettu lista.
        for (String tyyppi : tagTyypit) {
            queryBuilder.append(", ");
            queryBuilder.append(tyyppi);
            queryBuilder.append("_kysely.");
            queryBuilder.append(tyyppi);
            queryBuilder.append("_concat");
        }
    }

    private void upotaTagLiitot(String[] tagTyypit, StringBuilder queryBuilder) {
        // tehdään taginkaltaisista pilkuilla erotettu lista, joka on sarakkeessa tyyppi_kysely.tyyppi_concat
        for (String tyyppi : tagTyypit) {
            queryBuilder.append("LEFT JOIN (SELECT GROUP_CONCAT(tag) AS ");
            queryBuilder.append(tyyppi);
            queryBuilder.append("_concat, vinkki FROM (SELECT Tag.tag AS tag, VinkkiTag.vinkki AS vinkki FROM Tag, VinkkiTag WHERE Tag.tag_id = VinkkiTag.tag AND Tag.tyyppi = '");
            queryBuilder.append(tyyppi);
            queryBuilder.append("' ORDER BY VinkkiTag.vinkki) GROUP BY vinkki) AS ");
            queryBuilder.append(tyyppi);
            queryBuilder.append("_kysely ON Vinkki.vinkki_id = ");
            queryBuilder.append(tyyppi);
            queryBuilder.append("_kysely.vinkki ");
        }
    }

    // apumetodi apumetodille rakennaKaikkiTiedotQuery
    private void upotaSarakkeet(String taulunNimi, String[] sarakkeet, StringBuilder queryBuilder) {
        // kaikki sarakkeet muodossa Taulu.sarake AS taulu_sarake
        for (String sarake : sarakkeet) {
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
        // Kaikki muut oheishötöt tuhoutuvat automaattisesti, kun Vinkki poistetaan.
        String query = "DELETE FROM Vinkki WHERE vinkki_id = ?;";
        try (Connection conn = this.db.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, poistettavaID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            return false;
        }
    }
}
