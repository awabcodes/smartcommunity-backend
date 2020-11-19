package com.awabcodes.smartcommunity.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class PollTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poll.class);
        Poll poll1 = new Poll();
        poll1.setId(1L);
        Poll poll2 = new Poll();
        poll2.setId(poll1.getId());
        assertThat(poll1).isEqualTo(poll2);
        poll2.setId(2L);
        assertThat(poll1).isNotEqualTo(poll2);
        poll1.setId(null);
        assertThat(poll1).isNotEqualTo(poll2);
    }
}
