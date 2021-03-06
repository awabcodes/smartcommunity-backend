package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class PollDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollDTO.class);
        PollDTO pollDTO1 = new PollDTO();
        pollDTO1.setId(1L);
        PollDTO pollDTO2 = new PollDTO();
        assertThat(pollDTO1).isNotEqualTo(pollDTO2);
        pollDTO2.setId(pollDTO1.getId());
        assertThat(pollDTO1).isEqualTo(pollDTO2);
        pollDTO2.setId(2L);
        assertThat(pollDTO1).isNotEqualTo(pollDTO2);
        pollDTO1.setId(null);
        assertThat(pollDTO1).isNotEqualTo(pollDTO2);
    }
}
