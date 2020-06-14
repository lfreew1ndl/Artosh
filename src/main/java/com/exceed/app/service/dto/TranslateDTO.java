package com.exceed.app.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.exceed.app.domain.Translate} entity.
 */
public class TranslateDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String translate;

    private Long languageId;

    private String languageName;

    private Long wordId;

    private String wordWord;

    @JsonIgnore
    private Set<String> translateOptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public String getWordWord() {
        return wordWord;
    }

    public void setWordWord(String wordWord) {
        this.wordWord = wordWord;
    }

    public Set<String> getTranslateOptions() {
        return translateOptions;
    }

    public void setTranslateOptions(Set<String> translateOptions) {
        this.translateOptions = translateOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslateDTO)) {
            return false;
        }

        return id != null && id.equals(((TranslateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranslateDTO{" +
            "id=" + getId() +
            ", translate='" + getTranslate() + "'" +
            ", languageId=" + getLanguageId() +
            ", languageName='" + getLanguageName() + "'" +
            ", wordId=" + getWordId() +
            ", wordWord='" + getWordWord() + "'" +
            "}";
    }
}
