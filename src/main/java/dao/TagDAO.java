package dao;

import tietokantaobjektit.SuperTag;
import tietokantaobjektit.Tag;

public class TagDAO extends SuperTagDAO {

    public static final String TYYPPI = Tag.TYYPPI;

    public TagDAO(Tietokanta db) {
        super(db, TYYPPI);
    }
    
    public Tag haeTag(long id) {
        SuperTag superTag = super.haeTag(id);
        if(superTag==null) {
            return null;
        }
        return new Tag(superTag.getTag());
    }

}
