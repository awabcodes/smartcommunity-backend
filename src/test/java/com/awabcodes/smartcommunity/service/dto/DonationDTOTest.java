package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class DonationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationDTO.class);
        DonationDTO donationDTO1 = new DonationDTO();
        donationDTO1.setId(1L);
        DonationDTO donationDTO2 = new DonationDTO();
        assertThat(donationDTO1).isNotEqualTo(donationDTO2);
        donationDTO2.setId(donationDTO1.getId());
        assertThat(donationDTO1).isEqualTo(donationDTO2);
        donationDTO2.setId(2L);
        assertThat(donationDTO1).isNotEqualTo(donationDTO2);
        donationDTO1.setId(null);
        assertThat(donationDTO1).isNotEqualTo(donationDTO2);
    }
}
