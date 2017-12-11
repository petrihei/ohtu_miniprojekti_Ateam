package tekstikayttis;

import java.util.ArrayList;
import java.util.List;
import tietokantaobjektit.RelatedCourse;
import tietokantaobjektit.Tag;


public class Tagaaja {
    
    public List<Tag> tagienErottaminen(String syote) {
        List<Tag> tagLista = new ArrayList<>();
        String tagit[] = syote.split(",");
        for (int i = 0; i < tagit.length; i++) {
            String lisattava = tagit[i].trim();
            tagLista.add(new Tag(lisattava));
        }
        return tagLista;
    }

    List<RelatedCourse> kurssienErottaminen(String syote) {
        List<RelatedCourse> kurssiLista = new ArrayList<>();
        String tagit[] = syote.split(",");
        for (int i = 0; i < tagit.length; i++) {
            String lisattava = tagit[i].trim();
            kurssiLista.add(new RelatedCourse(lisattava));
        }
        return kurssiLista;
    }
    
}
