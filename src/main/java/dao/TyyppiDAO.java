package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tietokantaobjektit.Tag;
import tietokantaobjektit.Vinkki;

abstract public class TyyppiDAO {

    protected Tietokanta db;

    abstract protected String hakuSql();

    abstract protected String lisaysSql();

    abstract protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException;

    public Vinkki haeVinkki(long id) {
        Vinkki vinkki = null;
        try (ResultSet result = teeKysely(hakuSql(), new ArrayList(), id)) {
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
    
    private ResultSet teeKysely(String sql, List<String> tiedot, long id) throws SQLException {
        PreparedStatement st = valmisteleKysely(sql);
        asetaVinkinTiedotParametreiksi(st, tiedot, id);
        return st.executeQuery();
    }

    private void suoritaLause(String sql, List<String> tiedot, long id) throws SQLException {
        PreparedStatement st = valmisteleKysely(sql);
        asetaVinkinTiedotParametreiksi(st, tiedot, id);
        st.executeUpdate();
    }

    private PreparedStatement valmisteleKysely(String sql) throws SQLException {
        Connection conn = this.db.getConnection();
        return conn.prepareStatement(sql);
    }

    private void lisaaVinkilleTagitResultista(ResultSet result, Vinkki vinkki) throws SQLException {
        do {
            String tagString = result.getString("tag");
            if (tagString != null) {
                Tag tag = new Tag(tagString);
                vinkki.lisaaTag(tag);
            }
        } while (result.next());
    }

    private boolean lisaaTyyppiTietokantaan(Vinkki lisattava, long vinkkiId) {
        try {
            suoritaLause(lisaysSql(), lisattava.tyypinTiedotJarjestyksessa(), vinkkiId);
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
            lisaaVinkilleTagitResultista(result, vinkki);
        }
        return vinkki;
    }

    private void asetaVinkinTiedotParametreiksi(PreparedStatement st, List<String> tiedot, long id) throws SQLException {
        st.setLong(1, id);
        for (int i = 2; i <= tiedot.size() + 1; i++) {
            st.setString(i, tiedot.get(i - 2));
        }
    }
}
