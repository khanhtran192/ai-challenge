package com.mbbank.biz.pro.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentShareDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentShareDTO.class);
        DocumentShareDTO documentShareDTO1 = new DocumentShareDTO();
        documentShareDTO1.setId(1L);
        DocumentShareDTO documentShareDTO2 = new DocumentShareDTO();
        assertThat(documentShareDTO1).isNotEqualTo(documentShareDTO2);
        documentShareDTO2.setId(documentShareDTO1.getId());
        assertThat(documentShareDTO1).isEqualTo(documentShareDTO2);
        documentShareDTO2.setId(2L);
        assertThat(documentShareDTO1).isNotEqualTo(documentShareDTO2);
        documentShareDTO1.setId(null);
        assertThat(documentShareDTO1).isNotEqualTo(documentShareDTO2);
    }
}
