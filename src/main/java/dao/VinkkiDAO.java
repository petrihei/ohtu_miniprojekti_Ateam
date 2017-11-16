import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Chamion
 * DAO luokalle tietokantaobjektit.Vinkki
 */
public class VinkkiDAO {
	public static void lisaaVinkki(Vinkki lisattava) {
		Connection conn = DriverManager.getConnection("jdbc:MySql://localhost:3306/TietokantaTest1.db");
		Statement stmt = conn.createStatement();
	}
}