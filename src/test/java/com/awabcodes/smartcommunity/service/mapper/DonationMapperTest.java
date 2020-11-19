package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DonationMapperTest {

    private DonationMapper donationMapper;

    @BeforeEach
    public void setUp() {
        donationMapper = new DonationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(donationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(donationMapper.fromId(null)).isNull();
    }
}
