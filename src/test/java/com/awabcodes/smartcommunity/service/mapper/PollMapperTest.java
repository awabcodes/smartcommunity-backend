package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PollMapperTest {

    private PollMapper pollMapper;

    @BeforeEach
    public void setUp() {
        pollMapper = new PollMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pollMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pollMapper.fromId(null)).isNull();
    }
}
