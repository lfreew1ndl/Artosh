package com.exceed.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Translate.
 */
@Entity
@Table(name = "translate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Translate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "translate", length = 255, nullable = false)
    private String translate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "translates", allowSetters = true)
    private Language language;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "translates", allowSetters = true)
    private Word word;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTranslate() {
        return translate;
    }

    public Translate translate(String translate) {
        this.translate = translate;
        return this;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Language getLanguage() {
        return language;
    }

    public Translate language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Word getWord() {
        return word;
    }

    public Translate word(Word word) {
        this.word = word;
        return this;
    }

    public void setWord(Word word) {
        this.word = word;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Translate)) {
            return false;
        }
        return id != null && id.equals(((Translate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Translate{" +
            "id=" + getId() +
            ", translate='" + getTranslate() + "'" +
            "}";
    }
}
