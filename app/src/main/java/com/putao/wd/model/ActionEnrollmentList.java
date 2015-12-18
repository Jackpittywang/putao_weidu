package com.putao.wd.model;

import java.util.List;

/**
 * Created by yanghx on 2015/12/18.
 */
public class ActionEnrollmentList extends Page {
    private List<ActionEnrollment> comment;

    public List<ActionEnrollment> getComment() {
        return comment;
    }

    public void setComment(List<ActionEnrollment> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ActionEnrollmentList{" +
                "comment=" + comment +
                '}';
    }
}
