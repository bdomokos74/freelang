package org.bds.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Jan 3, 2010
 * Time: 10:52:15 AM
 */
@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "TAG_ID")
    private Long id;

    @Column(name = "TAG_NAME")
    private String name;

    public Tag() {
    }

    @Override
    public String toString() {
        return "[tag:"+name+"]";
    }

    public Tag(String name) {

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
