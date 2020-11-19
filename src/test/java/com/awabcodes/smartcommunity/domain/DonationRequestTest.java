package com.awabcodes.smartcommunity.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class DonationRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationRequest.class);
        DonationRequest donationRequest1 = new DonationRequest();
        donationRequest1.setId(1L);
        DonationRequest donationRequest2 = new DonationRequest();
        donationRequest2.setId(donationRequest1.getId());
        assertThat(donationRequest1).isEqualTo(donationRequest2);
        donationRequest2.setId(2L);
        assertThat(donationRequest1).isNotEqualTo(donationRequest2);
        donationRequest1.setId(null);
        assertThat(donationRequest1).isNotEqualTo(donationRequest2);
    }
}
