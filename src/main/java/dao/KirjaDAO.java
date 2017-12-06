package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Kirja;
import tietokantaobjektit.Vinkki;

public class KirjaDAO extends TyyppiDAO {

    public KirjaDAO(Tietokanta db) {
        this.db = db;
    }

    @Override
    protected String hakuSql() {
        return "SELECT * FROM Vinkki"
                + " JOIN Kirja ON vinkki.vinkki_id = kirja.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ?";
    }

    @Override
    protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException {
        return new Kirja(result.getString("otsikko"),
                result.getString("kuvaus"),
                result.getString("isbn"),
                result.getString("kirjailija"));
    }

    @Override
    protected String lisaysSql() {
        return "INSERT INTO Kirja (vinkki, ISBN, kirjailija) values (?, ?, ?)";
    }
}
