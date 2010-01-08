package org.bds.model;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Jan 3, 2010
 * Time: 10:49:58 AM
 */
public enum Language {
    EN("English"), ES("Spanish"), HU("Hungarian");
    private String name;

    Language(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
