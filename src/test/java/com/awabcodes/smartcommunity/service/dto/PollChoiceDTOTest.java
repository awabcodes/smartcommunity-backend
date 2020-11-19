package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class PollChoiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollChoiceDTO.class);
        PollChoiceDTO pollChoiceDTO1 = new PollChoiceDTO();
        pollChoiceDTO1.setId(1L);
        PollChoiceDTO pollChoiceDTO2 = new PollChoiceDTO();
        assertThat(pollChoiceDTO1).isNotEqualTo(pollChoiceDTO2);
        pollChoiceDTO2.setId(pollChoiceDTO1.getId());
        assertThat(pollChoiceDTO1).isEqualTo(pollChoiceDTO2);
        pollChoiceDTO2.setId(2L);
        assertThat(pollChoiceDTO1).isNotEqualTo(pollChoiceDTO2);
        pollChoiceDTO1.setId(null);
        assertThat(pollChoiceDTO1).isNotEqualTo(pollChoiceDTO2);
    }
}
