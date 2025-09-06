package com.mbbank.biz.pro.domain;

import static com.mbbank.biz.pro.domain.AppUserTestSamples.*;
import static com.mbbank.biz.pro.domain.AuditLogTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditLog.class);
        AuditLog auditLog1 = getAuditLogSample1();
        AuditLog auditLog2 = new AuditLog();
        assertThat(auditLog1).isNotEqualTo(auditLog2);

        auditLog2.setId(auditLog1.getId());
        assertThat(auditLog1).isEqualTo(auditLog2);

        auditLog2 = getAuditLogSample2();
        assertThat(auditLog1).isNotEqualTo(auditLog2);
    }

    @Test
    void userTest() {
        AuditLog auditLog = getAuditLogRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        auditLog.setUser(appUserBack);
        assertThat(auditLog.getUser()).isEqualTo(appUserBack);

        auditLog.user(null);
        assertThat(auditLog.getUser()).isNull();
    }

    @Test
    void documentTest() {
        AuditLog auditLog = getAuditLogRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        auditLog.setDocument(documentBack);
        assertThat(auditLog.getDocument()).isEqualTo(documentBack);

        auditLog.document(null);
        assertThat(auditLog.getDocument()).isNull();
    }
}
