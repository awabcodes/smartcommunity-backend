package com.awabcodes.smartcommunity.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class NeedOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NeedOrder.class);
        NeedOrder needOrder1 = new NeedOrder();
        needOrder1.setId(1L);
        NeedOrder needOrder2 = new NeedOrder();
        needOrder2.setId(needOrder1.getId());
        assertThat(needOrder1).isEqualTo(needOrder2);
        needOrder2.setId(2L);
        assertThat(needOrder1).isNotEqualTo(needOrder2);
        needOrder1.setId(null);
        assertThat(needOrder1).isNotEqualTo(needOrder2);
    }
}
