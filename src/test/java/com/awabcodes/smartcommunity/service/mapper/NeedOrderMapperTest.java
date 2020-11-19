package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NeedOrderMapperTest {

    private NeedOrderMapper needOrderMapper;

    @BeforeEach
    public void setUp() {
        needOrderMapper = new NeedOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(needOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(needOrderMapper.fromId(null)).isNull();
    }
}
