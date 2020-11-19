package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class NeedOrderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NeedOrderDTO.class);
        NeedOrderDTO needOrderDTO1 = new NeedOrderDTO();
        needOrderDTO1.setId(1L);
        NeedOrderDTO needOrderDTO2 = new NeedOrderDTO();
        assertThat(needOrderDTO1).isNotEqualTo(needOrderDTO2);
        needOrderDTO2.setId(needOrderDTO1.getId());
        assertThat(needOrderDTO1).isEqualTo(needOrderDTO2);
        needOrderDTO2.setId(2L);
        assertThat(needOrderDTO1).isNotEqualTo(needOrderDTO2);
        needOrderDTO1.setId(null);
        assertThat(needOrderDTO1).isNotEqualTo(needOrderDTO2);
    }
}
