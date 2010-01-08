package org.bds.model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Jan 1, 2010
 * Time: 6:23:51 PM
 */
@Entity
@Table(name = "ITEM")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_TEXT")
    private String text;

    @Column(name = "ITEM_TRANSLATION")
    private String translation;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANG", nullable = false, updatable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSLATION_LANG", nullable = false, updatable = false)
    private Language translationLanguage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    @org.hibernate.annotations.IndexColumn(name = "TAG_POSITION")
    @org.hibernate.annotations.BatchSize(size = 10)
    private List<Tag> tags = new ArrayList<Tag>();

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "NEXT_MESSAGE_ID")
//    private Item nextMessage;

    public Item() {
    }

    @Override
    public String toString() {
        return text + "->"+translation;
    }

    public Item(String text, Language textLanguage, String translation, Language translationLanguage) {
        this.text = text;
        this.language = textLanguage;
        this.translation = translation;
        this.translationLanguage = translationLanguage;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getTranslationLanguage() {
        return translationLanguage;
    }

    public void setTranslationLanguage(Language translationLanguage) {
        this.translationLanguage = translationLanguage;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag can't be null.");
        }
        tags.add(tag);
    }
}
