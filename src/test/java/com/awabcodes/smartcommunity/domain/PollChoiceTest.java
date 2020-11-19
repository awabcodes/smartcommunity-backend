package com.awabcodes.smartcommunity.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class PollChoiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollChoice.class);
        PollChoice pollChoice1 = new PollChoice();
        pollChoice1.setId(1L);
        PollChoice pollChoice2 = new PollChoice();
        pollChoice2.setId(pollChoice1.getId());
        assertThat(pollChoice1).isEqualTo(pollChoice2);
        pollChoice2.setId(2L);
        assertThat(pollChoice1).isNotEqualTo(pollChoice2);
        pollChoice1.setId(null);
        assertThat(pollChoice1).isNotEqualTo(pollChoice2);
    }
}
