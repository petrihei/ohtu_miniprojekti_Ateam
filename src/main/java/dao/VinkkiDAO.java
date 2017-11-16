package dao;

import com.mysql.jdbc.Driver;

import java.sql.*;

import tietokantaobjektit.Vinkki;

/**
 *
 * @author Chamion
 * DAO luokalle tietokantaobjektit.Vinkki
 */
public class VinkkiDAO {
	public static void lisaaVinkki(Vinkki lisattava) throws SQLException {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:MySql://localhost:3306/TietokantaTest1.db");
		} catch(SQLException e) {
			throw new SQLException("Yhteyden muodostus tietokantaan epäonnitui, " + e);
		}
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)");
		stmt.setString(1, lisattava.getOtsikko());
		stmt.setString(2, lisattava.getKuvaus());
		stmt.setString(3, lisattava.getTyyppi());
		stmt.executeUpdate();
	}
}