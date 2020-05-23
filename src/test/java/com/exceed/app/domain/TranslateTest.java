package com.exceed.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.exceed.app.web.rest.TestUtil;

public class TranslateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Translate.class);
        Translate translate1 = new Translate();
        translate1.setId(1L);
        Translate translate2 = new Translate();
        translate2.setId(translate1.getId());
        assertThat(translate1).isEqualTo(translate2);
        translate2.setId(2L);
        assertThat(translate1).isNotEqualTo(translate2);
        translate1.setId(null);
        assertThat(translate1).isNotEqualTo(translate2);
    }
}
