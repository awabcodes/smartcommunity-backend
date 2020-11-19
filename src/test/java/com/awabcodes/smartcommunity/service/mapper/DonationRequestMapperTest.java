package com.awabcodes.smartcommunity.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DonationRequestMapperTest {

    private DonationRequestMapper donationRequestMapper;

    @BeforeEach
    public void setUp() {
        donationRequestMapper = new DonationRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(donationRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(donationRequestMapper.fromId(null)).isNull();
    }
}
