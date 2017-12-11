/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tietokantaobjektit.Tag;
import tietokantaobjektit.Vinkki;

/**
 *
 * @author Chamion
 */
public class VinkkiDAOTest {

    private Tietokanta db;
    private VinkkiDAO dao;

    private static boolean isInitialized = false;

    @Before
    public void initTietokanta() {
        if (isInitialized) return;
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.dao = new VinkkiDAO(db);
        this.db.lisaaTestiData();
        this.isInitialized = true;
    }

    @Test
    public void hakuToimiiVinkinOtsikolla() {
        String tulos = dao.hae("Introduction").toString();
        tulosSisaltaaIntroductionToAlgorithms(tulos);
    }

    @Test
    public void hakuToimiiVinkinKuvauksella() {
        String tulos = dao.hae("algorithms are").toString();
        tulosSisaltaaIntroductionToAlgorithms(tulos);
    }

    @Test
    public void hakuToimiiVinkinTyypilla() {
        String tulos = dao.hae("kirja").toString();
        tulosSisaltaaIntroductionToAlgorithms(tulos);
    }

    @Test
    public void hakuToimiiKirjanISBNlla() {
        String tulos = dao.hae("9780262033848").toString();
        tulosSisaltaaIntroductionToAlgorithms(tulos);
    }

    @Test
    public void hakuToimiiKirjanKirjailijalla() {
        String tulos = dao.hae("James Cormen").toString();
        tulosSisaltaaIntroductionToAlgorithms(tulos);
    }

    @Test
    public void hakuToimiiPodcastinTekijalla() {
        String tulos = dao.hae("Donald Ewart").toString();
        tulosSisaltaaMasteryForScrumTeams(tulos);
    }

    @Test
    public void hakuToimiiPodcastinNimella() {
        String tulos = dao.hae("Master Toolbox").toString();
        tulosSisaltaaMasteryForScrumTeams(tulos);
    }

    @Test
    public void hakuToimiiPodcastinUrlilla() {
        String tulos = dao.hae("scrum-master-toolbox.org").toString();
        tulosSisaltaaMasteryForScrumTeams(tulos);
    }

    @Test
    public void hakuToimiiPodcastinPvmlla() {
        String tulos = dao.hae("16.11.2017").toString();
        tulosSisaltaaMasteryForScrumTeams(tulos);
    }

    @Test
    public void hakuToimiiBloginKirjoittajalla() {
        String tulos = dao.hae("Sam Laing").toString();
        tulosSisaltaaRemoteAgileTeams(tulos);
    }

    @Test
    public void hakuToimiiVideonTekijalla() {
        String tulos = dao.hae("Sam Laing").toString();
        tulosSisaltaa60SecondScrumOrganizingTheProductBacklog(tulos);
    }

    @Test
    public void kaikkiVinkitJaTiedotHuonollaTietokannallaPalauttaaNull() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertEquals(null, huonoDao.kaikkiVinkitJaTiedot());
    }

    @Test
    public void lisaaVinkkiTagHuonollaTietokannallaPalauttaaFalse() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag("doot"));
        assertFalse(huonoDao.lisaaVinkkiTag(lisattava.getTagit(), 1l));
    }

    @Test
    public void kaikkiVinkitHuonollaTietokannallaPalauttaaTyhjanListan() {
        Tietokanta huonoDB = new Tietokanta("jdbc:sqlite:TyhjaTestausTietokanta.db");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertTrue(huonoDao.kaikkiVinkit().isEmpty());
    }

    @Test
    public void lisaaVinkkiPalauttaaNeg1KunTagOnHuono() {
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag(null));
        assertEquals(-1l, dao.lisaaVinkki(lisattava));
    }

    @Test
    public void kaikkiVinkitJaTiedotHuonollaTietokantaosoitteellaPalauttaaNull() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertEquals(null, huonoDao.kaikkiVinkitJaTiedot());
    }

    @Test
    public void lisaaVinkkiTagHuonollaTietokantaosoitteellaPalauttaaFalse() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        Vinkki lisattava = new Vinkki("ots", "kuv", "kirja");
        lisattava.lisaaTag(new Tag("doot"));
        assertFalse(huonoDao.lisaaVinkkiTag(lisattava.getTagit(), 1l));
    }

    @Test
    public void kaikkiVinkitHuonollaTietokantaosoitteellaPalauttaaTyhjanListan() {
        Tietokanta huonoDB = new Tietokanta("");
        VinkkiDAO huonoDao = new VinkkiDAO(huonoDB);
        assertTrue(huonoDao.kaikkiVinkit().isEmpty());
    }

    private void tulosSisaltaaIntroductionToAlgorithms(String tulos) {
        assertTrue(tulos.contains("kirja: Introduction to Algorithms"));
        assertTrue(tulos.contains("Kuvaus: The algorithms are described in English and "
                + "in a pseudocode designed to be readable by anyone who has done a little programming."));
        assertTrue(tulos.contains("ISBN: 9780262033848"));
        assertTrue(tulos.contains("Kirjailija: James Cormen"));
    }

    private void tulosSisaltaaMasteryForScrumTeams(String tulos) {
        assertTrue(tulos.contains("podcastjakso: Donald Ewart the 3 steps to mastery for Scrum teams."));
        assertTrue(tulos.contains("Kuvaus: Powerful Questions is recommended as a tool that helps Scrum "
                + "Masters to initiate right kind of discussions with the team."));
        assertTrue(tulos.contains("Tekijä: Donald Ewart"));
        assertTrue(tulos.contains("Nimi: Scrum Master Toolbox Podcast"));
        assertTrue(tulos.contains("Url: http://scrum-master-toolbox.org/2017/11/podcast/donald-ewart-the-3-steps-to-mastery-for-scrum-teams/"));
        assertTrue(tulos.contains("Pvm: 16.11.2017"));
    }

    private void tulosSisaltaaRemoteAgileTeams(String tulos) {
        assertTrue(tulos.contains("blogipostaus: Remote Agile Teams"));
        assertTrue(tulos.contains("Kuvaus: Results of a survey about how agile teams work when team members don't work in the same place."));
        assertTrue(tulos.contains("Kirjoittaja: Sam Laing"));
        assertTrue(tulos.contains("Nimi: Growing Agile Blog"));
        assertTrue(tulos.contains("Url: https://www.growingagile.co.za/2017/07/remote-agile-teams/"));
        assertTrue(tulos.contains("Pvm: 25.7.2017"));
    }

    private void tulosSisaltaa60SecondScrumOrganizingTheProductBacklog(String tulos) {
        assertTrue(tulos.contains("video: 60 Second Scrum Organizing the Product Backlog"));
        assertTrue(tulos.contains("Kuvaus: Is your Product Backlog growing too large to manage?  Here are some tips to get it under control."));
        assertTrue(tulos.contains("Tekijä: Angela Druckman"));
        assertTrue(tulos.contains("Url: https://www.youtube.com/watch?v=g92anflQgyY"));
        assertTrue(tulos.contains("Pvm: 26.11.2012"));
    }
}
