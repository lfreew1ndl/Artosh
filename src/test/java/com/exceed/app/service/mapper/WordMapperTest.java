package com.exceed.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordMapperTest {
    private WordMapper wordMapper;

    @BeforeEach
    public void setUp() {
        wordMapper = new WordMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(wordMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wordMapper.fromId(null)).isNull();
    }
}
