package logiikka;

import java.util.List;

import dao.*;
import tietokantaobjektit.*;

public class Logiikka {

    private Tietokanta db;
    private VinkkiDAO vinkkiDao;
    private KirjaDAO kirjaDao;
    private VideoDAO videoDao;
    private BlogiDAO blogiDao;
    private PodcastDAO podcastDao;

    public Logiikka(Tietokanta db) {
        this.db = db;
        this.vinkkiDao = new VinkkiDAO(db);
        this.kirjaDao = new KirjaDAO(db);
        this.videoDao = new VideoDAO(db);
        this.blogiDao = new BlogiDAO(db);
        this.podcastDao = new PodcastDAO(db);
    }

    public List<Vinkki> kaikkiVinkit() {
        return vinkkiDao.kaikkiVinkit();
    }

    public Vinkki lisaaVinkki(Vinkki vinkki) {
        // TODO: Käytä super-DAO:n lisaaVinkki-metodia
        if (vinkki.getTyyppi().equals("kirja")) {
            return lisaaKirja((Kirja) vinkki);
        }
        if (vinkki.getTyyppi().equals("video")) {
            return lisaaVideo((Video) vinkki);
        }
        if (vinkki.getTyyppi().equals("blogi")) {
            return lisaaBlogi((Blogi) vinkki);
        }
        if (vinkki.getTyyppi().equals("podcast")) {
            return lisaaPodcast((Podcast) vinkki);
        }
        return null;
    }

    private Kirja lisaaKirja(Kirja kirja) {
        long id = kirjaDao.lisaaKirja(kirja);
        Kirja uusiKirja = kirjaDao.haeKirja(id);
        return uusiKirja;
    }

    private Video lisaaVideo(Video video) {
        long id = videoDao.lisaaVideo(video);
        Video uusiVideo = videoDao.haeVideo(id);
        return uusiVideo;
    }
    
    private Blogi lisaaBlogi(Blogi blogi) {
        long id = blogiDao.lisaaBlogi(blogi);
        Blogi uusiBlogi = blogiDao.haeBlogi(id);
        return uusiBlogi;
    }
    
    private Vinkki lisaaPodcast(Podcast podcast) {
        long id = podcastDao.lisaaPodcast(podcast);
        Podcast uusiPodcast = podcastDao.haePodcast(id);
        return uusiPodcast;
    }
    
    public boolean VinkinPoisto(Vinkki Poistettava){
      System.out.println("Logiikassa Poistettava " + Poistettava.getId());
      return vinkkiDao.PoistaVinkki(Poistettava);
    }


    public List<Vinkki> haeKaikkiVinkit() {
        return vinkkiDao.kaikkiVinkitJaTiedot();
    }
}
