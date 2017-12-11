package dao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tietokantaobjektit.Vinkki;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Ignacio Perez
 *
 */


public class VinkinPoistoDAOTest {

    private Tietokanta db;
    private VinkkiDAO dao;

    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new VinkkiDAO(db);
    }

    @Test
    public void VinkinPoistoTesti(){
        initTietokanta();
        //Lisätään Vinkki
        long SecondVid = -1;
        long Vid = -1;
        long TagId = -1;
        long TagIdSecond = -1;
        String query = "INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)";
        try{

            Connection conn = this.db.getConnection();

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "Poisto kirja");
            stmt.setString(2, "Poistetaan");
            stmt.setString(3, "kirja");
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                Vid = rs.getLong(1);
            }


            query = "INSERT INTO Kirja (vinkki, ISBN, kirjailija) values (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, Vid);
            stmt.setString(2, "Poisto Kirja");
            stmt.setString(3, "Poistajainen Matti");
            stmt.executeUpdate();

            query = "INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) values (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "Poisto Video");
            stmt.setString(2, "Poistetaan");
            stmt.setString(3, "video");
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                SecondVid = rs.getLong(1);
            }

            query = "INSERT INTO Video (vinkki, tekija, url, pvm) values (?, ?, ?, ?)";

            stmt = conn.prepareStatement(query);
            stmt.setLong(1, SecondVid);
            stmt.setString(2, "Matti Poistaja");
            stmt.setString(3, "www.poistetaan.po");
            stmt.setString(4, "02.12.2017");
            stmt.executeUpdate();


            query = "INSERT INTO Tag (tag) values (?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,"Poistetaan Kohta");
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                TagId = rs.getLong(1);
            }

            query = "INSERT INTO Tag (tag) values (?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1,"Varmaan poistetaan");
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                TagIdSecond = rs.getLong(1);
            }

            query = "INSERT INTO VinkkiTag values (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1,Vid);
            stmt.setLong(2, TagId);
            stmt.executeUpdate();

            query = "INSERT INTO VinkkiTag values (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1,SecondVid);
            stmt.setLong(2, TagIdSecond);
            stmt.executeUpdate();

            query = "INSERT INTO VinkkiTag values (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setLong(1,Vid);
            stmt.setLong(2, TagIdSecond);
            stmt.executeUpdate();


            //Poistetaan Lisätty Video-vinkki
            List<Vinkki> vinkit = dao.kaikkiVinkit();
            Vinkki vinkki = vinkit.get(vinkit.size()-1);
            dao.poistaVinkki(vinkki);

            //Testataan Poisto
            query = "Select * from Vinkki where vinkki_id = " + SecondVid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from VinkkiTag where vinkki = " + SecondVid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from Kirja where vinkki = " + SecondVid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from Tag where tag = " + TagIdSecond;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                assertEquals(rs.getLong(1), Vid);
            }

            //Nyt poistetaan toinen vinkki
            vinkki = vinkit.get(vinkit.size()-2);
            System.out.println(vinkki.getId());
            dao.poistaVinkki(vinkki);


            //Testataan Poisto
            query = "Select * from Vinkki where vinkki_id = " + Vid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from VinkkiTag where vinkki = " + Vid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from Kirja where vinkki = " + Vid;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from Tag where tag = " + TagId;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());

            query = "Select * from Tag where tag = " + TagIdSecond;
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            assertTrue(!rs.next());



        }  catch (SQLException ex) {
            System.out.println("SQL kysely epäonnistui: " + ex);
        }



    }

}
