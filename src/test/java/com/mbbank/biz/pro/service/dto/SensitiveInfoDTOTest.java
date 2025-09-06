package com.mbbank.biz.pro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensitiveInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensitiveInfoDTO.class);
        SensitiveInfoDTO sensitiveInfoDTO1 = new SensitiveInfoDTO();
        sensitiveInfoDTO1.setId(1L);
        SensitiveInfoDTO sensitiveInfoDTO2 = new SensitiveInfoDTO();
        assertThat(sensitiveInfoDTO1).isNotEqualTo(sensitiveInfoDTO2);
        sensitiveInfoDTO2.setId(sensitiveInfoDTO1.getId());
        assertThat(sensitiveInfoDTO1).isEqualTo(sensitiveInfoDTO2);
        sensitiveInfoDTO2.setId(2L);
        assertThat(sensitiveInfoDTO1).isNotEqualTo(sensitiveInfoDTO2);
        sensitiveInfoDTO1.setId(null);
        assertThat(sensitiveInfoDTO1).isNotEqualTo(sensitiveInfoDTO2);
    }
}
