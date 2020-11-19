package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class DonationRequestDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationRequestDTO.class);
        DonationRequestDTO donationRequestDTO1 = new DonationRequestDTO();
        donationRequestDTO1.setId(1L);
        DonationRequestDTO donationRequestDTO2 = new DonationRequestDTO();
        assertThat(donationRequestDTO1).isNotEqualTo(donationRequestDTO2);
        donationRequestDTO2.setId(donationRequestDTO1.getId());
        assertThat(donationRequestDTO1).isEqualTo(donationRequestDTO2);
        donationRequestDTO2.setId(2L);
        assertThat(donationRequestDTO1).isNotEqualTo(donationRequestDTO2);
        donationRequestDTO1.setId(null);
        assertThat(donationRequestDTO1).isNotEqualTo(donationRequestDTO2);
    }
}
