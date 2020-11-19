package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnnouncementMapperTest {

    private AnnouncementMapper announcementMapper;

    @BeforeEach
    public void setUp() {
        announcementMapper = new AnnouncementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(announcementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(announcementMapper.fromId(null)).isNull();
    }
}
