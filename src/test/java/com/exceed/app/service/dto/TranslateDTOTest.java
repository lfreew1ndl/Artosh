package com.exceed.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.exceed.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class TranslateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TranslateDTO.class);
        TranslateDTO translateDTO1 = new TranslateDTO();
        translateDTO1.setId(1L);
        TranslateDTO translateDTO2 = new TranslateDTO();
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
        translateDTO2.setId(translateDTO1.getId());
        assertThat(translateDTO1).isEqualTo(translateDTO2);
        translateDTO2.setId(2L);
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
        translateDTO1.setId(null);
        assertThat(translateDTO1).isNotEqualTo(translateDTO2);
    }
}
