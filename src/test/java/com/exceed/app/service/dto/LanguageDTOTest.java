package com.exceed.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.exceed.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class LanguageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguageDTO.class);
        LanguageDTO languageDTO1 = new LanguageDTO();
        languageDTO1.setId(1L);
        LanguageDTO languageDTO2 = new LanguageDTO();
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
        languageDTO2.setId(languageDTO1.getId());
        assertThat(languageDTO1).isEqualTo(languageDTO2);
        languageDTO2.setId(2L);
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
        languageDTO1.setId(null);
        assertThat(languageDTO1).isNotEqualTo(languageDTO2);
    }
}
