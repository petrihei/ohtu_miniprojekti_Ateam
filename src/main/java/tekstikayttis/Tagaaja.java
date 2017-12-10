package tekstikayttis;

import java.util.ArrayList;
import java.util.List;
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
    
}
