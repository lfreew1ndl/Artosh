package com.exceed.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExtra implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "gramar_score", nullable = false)
    private Integer gramarScore;

    @NotNull
    @Min(value = 0)
    @Column(name = "vocabulary_score", nullable = false)
    private Integer vocabularyScore;

    @NotNull
    @Min(value = 0)
    @Column(name = "lisning_score", nullable = false)
    private Integer lisningScore;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userExtras", allowSetters = true)
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGramarScore() {
        return gramarScore;
    }

    public UserExtra gramarScore(Integer gramarScore) {
        this.gramarScore = gramarScore;
        return this;
    }

    public void setGramarScore(Integer gramarScore) {
        this.gramarScore = gramarScore;
    }

    public Integer getVocabularyScore() {
        return vocabularyScore;
    }

    public UserExtra vocabularyScore(Integer vocabularyScore) {
        this.vocabularyScore = vocabularyScore;
        return this;
    }

    public void setVocabularyScore(Integer vocabularyScore) {
        this.vocabularyScore = vocabularyScore;
    }

    public Integer getLisningScore() {
        return lisningScore;
    }

    public UserExtra lisningScore(Integer lisningScore) {
        this.lisningScore = lisningScore;
        return this;
    }

    public void setLisningScore(Integer lisningScore) {
        this.lisningScore = lisningScore;
    }

    public User getUser() {
        return user;
    }

    public UserExtra user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public UserExtra language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", gramarScore=" + getGramarScore() +
            ", vocabularyScore=" + getVocabularyScore() +
            ", lisningScore=" + getLisningScore() +
            "}";
    }
}
