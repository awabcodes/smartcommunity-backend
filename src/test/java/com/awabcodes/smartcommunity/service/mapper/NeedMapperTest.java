package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NeedMapperTest {

    private NeedMapper needMapper;

    @BeforeEach
    public void setUp() {
        needMapper = new NeedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(needMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(needMapper.fromId(null)).isNull();
    }
}
