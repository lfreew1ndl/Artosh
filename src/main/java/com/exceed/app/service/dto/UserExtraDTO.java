package com.exceed.app.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.exceed.app.domain.UserExtra} entity.
 */
public class UserExtraDTO implements Serializable {
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer gramarScore;

    @NotNull
    @Min(value = 0)
    private Integer vocabularyScore;

    @NotNull
    @Min(value = 0)
    private Integer lisningScore;

    private Long userId;

    private String userLogin;

    private Long languageId;

    private String languageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGramarScore() {
        return gramarScore;
    }

    public void setGramarScore(Integer gramarScore) {
        this.gramarScore = gramarScore;
    }

    public Integer getVocabularyScore() {
        return vocabularyScore;
    }

    public void setVocabularyScore(Integer vocabularyScore) {
        this.vocabularyScore = vocabularyScore;
    }

    public Integer getLisningScore() {
        return lisningScore;
    }

    public void setLisningScore(Integer lisningScore) {
        this.lisningScore = lisningScore;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtraDTO)) {
            return false;
        }

        return id != null && id.equals(((UserExtraDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", gramarScore=" + getGramarScore() +
            ", vocabularyScore=" + getVocabularyScore() +
            ", lisningScore=" + getLisningScore() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", languageId=" + getLanguageId() +
            ", languageName='" + getLanguageName() + "'" +
            "}";
    }
}
