package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Video;
import tietokantaobjektit.Vinkki;

/**
 *
 * @author Chamion
 */
public class VideoDAO extends TyyppiDAO {

    public VideoDAO(Tietokanta db) {
        this.db = db;
    }

    @Override
    protected String hakuSql() {
        return "SELECT * FROM Vinkki"
                + " JOIN Video ON vinkki.vinkki_id = Video.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ?";
    }

    @Override
    protected String lisaysSql() {
        return "INSERT INTO Video (vinkki, tekija, url, pvm) values (?, ?, ?, ?)";
    }

    @Override
    protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException {
        return new Video(result.getString("otsikko"),
                result.getString("kuvaus"),
                result.getString("tekija"),
                result.getString("url"),
                result.getString("pvm"));
    }
}
