package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

/**
 *
 * @author Chamion Luokka kaikkien DAOjen yhteiskäyttöön.
 */
public class Tietokanta {

    private String jdbcOsoite;

    /**
     * Parametri "jdbc:sqlite:TietokantaTest1.db" näyttää toimivan.
     *
     * @param jdbcAddress osoite, joka osoittaa käytettävään tietokantaan.
     */
    public Tietokanta(String jdbcOsoite) {
        this.jdbcOsoite = jdbcOsoite;
    }

    /**
     * Luo yhteyden tietokantaan sqlite driverin avulla.
     *
     * @return java.sql.Connection tyyppinen yhteys.
     */
    public Connection getConnection() {
        Connection conn;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.resetOpenMode(SQLiteOpenMode.CREATE); // Ei salli tietokannan luomista.
            config.enforceForeignKeys(true); // Foreign key ei saa osoittaa tyhjään
            conn = DriverManager.getConnection(this.jdbcOsoite, config.toProperties());
        } catch (SQLException e) {
            // Älähtää, jos jdbcAddress on huono.
            System.err.println("Yhteyden muodostus tietokantaan epäonnistui, " + e);
            return null;
        }
        return conn;
    }

    public void poistaKaikki() {
        // TODO
    }

    public void lisaaTestiData() {
        List<String> lauseet = sqliteLauseet();
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }

        // TODO
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();
//        lista.add("CREATE TABLE Vinkki (vinkki_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
//                + "otsikko TEXT NOT NULL, kuvaus TEXT, tyyppi TEXT NOT NULL DEFAULT 'kirja');");
//        lista.add("CREATE TABLE Tag (tag_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tag TEXT NOT NULL);");
//        lista.add("CREATE TABLE VinkkiTag (vinkki INTEGER NOT NULL, tag INTEGER NOT NULL, "
//                + "FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id), "
//                + "FOREIGN KEY(tag) REFERENCES Tag(tag_id));");
//        lista.add("CREATE TABLE Kirja (vinkki INTEGER NOT NULL, ISBN TEXT, "
//                + "kirjailija TEXT, FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id));");
//        lista.add("CREATE TABLE Video (vinkki INTEGER NOT NULL, tekija TEXT, "
//                + "url TEXT NOT NULL, pvm TEXT, FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id));");
//        lista.add("CREATE TABLE Blogi (vinkki INTEGER NOT NULL, kirjoittaja TEXT, nimi TEXT, " 
//                + "url TEXT NOT NULL, pvm TEXT, FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id));");
//        lista.add("CREATE TABLE Podcast (vinkki INTEGER NOT NULL, tekija TEXT, nimi TEXT, " 
//                + "url TEXT NOT NULL, pvm TEXT, FOREIGN KEY(vinkki) REFERENCES Vinkki(vinkki_id));");

        //kirja
        lista.add("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) VALUES (\"Introduction to Algorithms\",\"The algorithms are described in English and in a pseudocode designed to be readable by anyone who has done a little programming.\", \"kirja\");");
        lista.add("INSERT INTO Kirja (vinkki, ISBN, kirjailija) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),\"9780262033848\" ,\"James Cormen\");");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"algoritmit\", \"tag\" );");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag ORDER BY tag_id DESC LIMIT 1));");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"TiRa\", \"related_course\");");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag ORDER BY tag_id DESC LIMIT 1));");

        //podcast
        lista.add("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) VALUES (\"Donald Ewart the 3 steps to mastery for Scrum teams.\", \"Powerful Questions is recommended as a tool that helps Scrum Masters to initiate right kind of discussions with the team.\", \"podcast\");");
        lista.add("INSERT INTO Podcast (vinkki, tekija, nimi, url, pvm) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),\"Donald Ewart\", \"Scrum Master Toolbox Podcast\", \"http://scrum-master-toolbox.org/2017/11/podcast/donald-ewart-the-3-steps-to-mastery-for-scrum-teams/\", \"16.11.2017\");");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"scrum\", \"tag\");");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"OhTu\", \"related_course\");");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag WHERE tag = \"scrum\" LIMIT 1));");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag WHERE tag = \"OhTu\" LIMIT 1));");

        //blogi
        lista.add("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) VALUES (\"Remote Agile Teams\",\"Results of a survey about how agile teams work when team members don't work in the same place.\", \"blogi\");");
        lista.add("INSERT INTO Blogi (vinkki, kirjoittaja, nimi, url, pvm) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),\"Sam Laing\", \"Growing Agile Blog\", \"https://www.growingagile.co.za/2017/07/remote-agile-teams/\", \"25.7.2017\");");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"agile\", \"tag\");");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag WHERE tag = \"agile\" LIMIT 1));");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag WHERE tag = \"OhTu\" LIMIT 1));");

        //video
        lista.add("INSERT INTO Vinkki (otsikko, kuvaus, tyyppi) VALUES (\"60 Second Scrum Organizing the Product Backlog\",\"Is your Product Backlog growing too large to manage?  Here are some tips to get it under control.\", \"video\");");
        lista.add("INSERT INTO Video (vinkki, tekija, url, pvm) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),\"Angela Druckman\", \"https://www.youtube.com/watch?v=g92anflQgyY\", \"26.11.2012\");");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag WHERE tag = \"OhTu\" LIMIT 1));");
        lista.add("INSERT INTO Tag (tag, tyyppi) VALUES (\"product backlog\", \"tag\");");
        lista.add("INSERT INTO VinkkiTag (vinkki, tag) VALUES ((SELECT vinkki_id FROM Vinkki ORDER BY vinkki_id DESC LIMIT 1),(SELECT tag_id FROM Tag ORDER BY tag_id DESC LIMIT 1));");

        return lista;
    }

}
