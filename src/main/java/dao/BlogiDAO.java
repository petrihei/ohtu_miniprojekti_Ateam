package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Blogi;
import tietokantaobjektit.Vinkki;

public class BlogiDAO extends TyyppiDAO {

    public BlogiDAO(Tietokanta db) {
        this.db = db;
    }

    @Override
    protected String hakuSql() {
        return "SELECT * FROM Vinkki"
                + " JOIN Blogi ON vinkki.vinkki_id = blogi.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ?";
    }

    @Override
    protected String lisaysSql() {
        return "INSERT INTO Blogi (vinkki, kirjoittaja, nimi, url, pvm) values (?, ?, ?, ?, ?)";
    }

    @Override
    protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException {
        return new Blogi(result.getString("otsikko"),
                result.getString("kuvaus"),
                result.getString("kirjoittaja"),
                result.getString("nimi"),
                result.getString("url"),
                result.getString("pvm"));
    }
}
