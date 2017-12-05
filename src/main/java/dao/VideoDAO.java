/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class VideoDAO extends BoundaryBase {

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

    @Override
    protected void asetaVinkinTiedotParametreiksi(PreparedStatement st, Vinkki lisattava) throws SQLException {
        Video video = (Video) lisattava;
        st.setString(2, video.getTekija());
        st.setString(3, video.getUrl());
        st.setString(4, video.getPvm());
    }
}
