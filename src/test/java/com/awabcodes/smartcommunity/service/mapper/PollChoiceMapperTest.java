package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PollChoiceMapperTest {

    private PollChoiceMapper pollChoiceMapper;

    @BeforeEach
    public void setUp() {
        pollChoiceMapper = new PollChoiceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pollChoiceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pollChoiceMapper.fromId(null)).isNull();
    }
}
