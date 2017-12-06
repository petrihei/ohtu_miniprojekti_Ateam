package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Tag;
import tietokantaobjektit.Vinkki;

abstract public class TyyppiDAO {

    protected Tietokanta db;

    abstract protected String hakuSql();

    abstract protected String lisaysSql();

    abstract protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException;

    abstract protected void asetaVinkinTiedotParametreiksi(PreparedStatement st, Vinkki lisattava) throws SQLException;

    public Vinkki haeVinkki(long id) {
        Vinkki vinkki = null;
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(hakuSql())) {
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            vinkki = luoVinkkiTageilla(result);
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        } catch (NullPointerException ex) {
            // Tietokanta-luokka tekee virheilmoituksen.
        }
        return vinkki;
    }

    public long lisaaVinkki(Vinkki lisattava) {
        // Lisätään ensin Vinkki.
        VinkkiDAO vinkkiDao = new VinkkiDAO(db);
        long vinkkiId = vinkkiDao.lisaaVinkki(lisattava);

        if (vinkkiId == -1) {
            return -1;
        }

        // Lisätään Kirja/Blogi/etc ja yhdistetään Vinkkiin.
        if (!lisaaTyyppiTietokantaan(lisattava, vinkkiId)) {
            return -1;
        }

        lisattava.setId(vinkkiId);
        return vinkkiId;
    }

    private void lisaaTagitResultista(ResultSet result, Vinkki vinkki) throws SQLException {
        do {
            String tagString = result.getString("tag");
            if (tagString != null) {
                Tag tag = new Tag(tagString);
                vinkki.lisaaTag(tag);
            }
        } while (result.next());
    }

    private boolean lisaaTyyppiTietokantaan(Vinkki lisattava, long vinkkiId) {
        try (Connection conn = this.db.getConnection();
                PreparedStatement st = conn.prepareStatement(lisaysSql())) {
            st.setLong(1, vinkkiId);
            asetaVinkinTiedotParametreiksi(st, lisattava);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
            //TBD Poista vinkki
            return false;
        }
        return true;
    }

    private Vinkki luoVinkkiTageilla(ResultSet result) throws SQLException {
        Vinkki vinkki = null;
        if (result.next()) {
            vinkki = luoVinkkiResultista(result);
            lisaaTagitResultista(result, vinkki);
        }
        return vinkki;
    }
}
