package com.exceed.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TranslateMapperTest {
    private TranslateMapper translateMapper;

    @BeforeEach
    public void setUp() {
        translateMapper = new TranslateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(translateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(translateMapper.fromId(null)).isNull();
    }
}
