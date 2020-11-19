package com.awabcodes.smartcommunity.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.awabcodes.smartcommunity.web.rest.TestUtil;

public class NeedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NeedDTO.class);
        NeedDTO needDTO1 = new NeedDTO();
        needDTO1.setId(1L);
        NeedDTO needDTO2 = new NeedDTO();
        assertThat(needDTO1).isNotEqualTo(needDTO2);
        needDTO2.setId(needDTO1.getId());
        assertThat(needDTO1).isEqualTo(needDTO2);
        needDTO2.setId(2L);
        assertThat(needDTO1).isNotEqualTo(needDTO2);
        needDTO1.setId(null);
        assertThat(needDTO1).isNotEqualTo(needDTO2);
    }
}
