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
       // this.blogiDao = new BlogiDAO(db);
       // this.podcastDao = new PodcastDAO(db);
    }

    public List<Vinkki> kaikkiVinkit() {
        return vinkkiDao.kaikkiVinkit();
    }

   public Vinkki lisaaVinkki(Vinkki vinkki) {
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
        long id = kirjaDao.lisaaVinkki(kirja);
        Kirja uusiKirja = (Kirja) kirjaDao.haeVinkki(id);
        return uusiKirja;
    }

    private Video lisaaVideo(Video video) {
        long id = videoDao.lisaaVinkki(video);
        Video uusiVideo = (Video) videoDao.haeVinkki(id);
        return uusiVideo;
    }

    private Blogi lisaaBlogi(Blogi blogi) {
        long id = blogiDao.lisaaVinkki(blogi);
        Blogi uusiBlogi = (Blogi) blogiDao.haeVinkki(id);
        return uusiBlogi;
    }

    private Vinkki lisaaPodcast(Podcast podcast) {
        long id = podcastDao.lisaaVinkki(podcast);
        Podcast uusiPodcast = (Podcast) podcastDao.haeVinkki(id);
        return uusiPodcast;
    }

    public List<Vinkki> hae (String hakutermi){
        return vinkkiDao.hae(hakutermi);
    }

    public boolean poistaVinkki(Vinkki poistettava) {
        return vinkkiDao.poistaVinkki(poistettava);
    }

    public List<Vinkki> haeKaikkiVinkit() {
        return vinkkiDao.kaikkiVinkitJaTiedot();
    }
}
