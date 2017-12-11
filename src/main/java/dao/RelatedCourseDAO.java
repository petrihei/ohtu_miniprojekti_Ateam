/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import tietokantaobjektit.RelatedCourse;
import tietokantaobjektit.SuperTag;

/**
 *
 * @author Chamion
 */
public class RelatedCourseDAO extends SuperTagDAO {

    public static final String TYYPPI = RelatedCourse.TYYPPI;

    public RelatedCourseDAO(Tietokanta db) {
        super(db, TYYPPI);
    }
    
    public RelatedCourse haeTag(long id) {
        SuperTag superTag = super.haeTag(id);
        if(superTag==null) {
            return null;
        }
        return new RelatedCourse(superTag.getTag());
    }

}
