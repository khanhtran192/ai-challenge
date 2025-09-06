package com.mbbank.biz.pro.domain;

import static com.mbbank.biz.pro.domain.DocumentTestSamples.*;
import static com.mbbank.biz.pro.domain.SensitiveInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensitiveInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensitiveInfo.class);
        SensitiveInfo sensitiveInfo1 = getSensitiveInfoSample1();
        SensitiveInfo sensitiveInfo2 = new SensitiveInfo();
        assertThat(sensitiveInfo1).isNotEqualTo(sensitiveInfo2);

        sensitiveInfo2.setId(sensitiveInfo1.getId());
        assertThat(sensitiveInfo1).isEqualTo(sensitiveInfo2);

        sensitiveInfo2 = getSensitiveInfoSample2();
        assertThat(sensitiveInfo1).isNotEqualTo(sensitiveInfo2);
    }

    @Test
    void documentTest() {
        SensitiveInfo sensitiveInfo = getSensitiveInfoRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        sensitiveInfo.setDocument(documentBack);
        assertThat(sensitiveInfo.getDocument()).isEqualTo(documentBack);

        sensitiveInfo.document(null);
        assertThat(sensitiveInfo.getDocument()).isNull();
    }
}
