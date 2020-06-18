package com.exceed.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserExtraMapperTest {
    private UserExtraMapper userExtraMapper;

    @BeforeEach
    public void setUp() {
        userExtraMapper = new UserExtraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userExtraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userExtraMapper.fromId(null)).isNull();
    }
}
