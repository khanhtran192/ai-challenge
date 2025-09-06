package com.mbbank.biz.pro.domain;

import static com.mbbank.biz.pro.domain.AppUserTestSamples.*;
import static com.mbbank.biz.pro.domain.AuditLogTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentShareTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentTestSamples.*;
import static com.mbbank.biz.pro.domain.SensitiveInfoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void sensitivesTest() {
        Document document = getDocumentRandomSampleGenerator();
        SensitiveInfo sensitiveInfoBack = getSensitiveInfoRandomSampleGenerator();

        document.addSensitives(sensitiveInfoBack);
        assertThat(document.getSensitives()).containsOnly(sensitiveInfoBack);
        assertThat(sensitiveInfoBack.getDocument()).isEqualTo(document);

        document.removeSensitives(sensitiveInfoBack);
        assertThat(document.getSensitives()).doesNotContain(sensitiveInfoBack);
        assertThat(sensitiveInfoBack.getDocument()).isNull();

        document.sensitives(new HashSet<>(Set.of(sensitiveInfoBack)));
        assertThat(document.getSensitives()).containsOnly(sensitiveInfoBack);
        assertThat(sensitiveInfoBack.getDocument()).isEqualTo(document);

        document.setSensitives(new HashSet<>());
        assertThat(document.getSensitives()).doesNotContain(sensitiveInfoBack);
        assertThat(sensitiveInfoBack.getDocument()).isNull();
    }

    @Test
    void sharesTest() {
        Document document = getDocumentRandomSampleGenerator();
        DocumentShare documentShareBack = getDocumentShareRandomSampleGenerator();

        document.addShares(documentShareBack);
        assertThat(document.getShares()).containsOnly(documentShareBack);
        assertThat(documentShareBack.getDocument()).isEqualTo(document);

        document.removeShares(documentShareBack);
        assertThat(document.getShares()).doesNotContain(documentShareBack);
        assertThat(documentShareBack.getDocument()).isNull();

        document.shares(new HashSet<>(Set.of(documentShareBack)));
        assertThat(document.getShares()).containsOnly(documentShareBack);
        assertThat(documentShareBack.getDocument()).isEqualTo(document);

        document.setShares(new HashSet<>());
        assertThat(document.getShares()).doesNotContain(documentShareBack);
        assertThat(documentShareBack.getDocument()).isNull();
    }

    @Test
    void auditLogsTest() {
        Document document = getDocumentRandomSampleGenerator();
        AuditLog auditLogBack = getAuditLogRandomSampleGenerator();

        document.addAuditLogs(auditLogBack);
        assertThat(document.getAuditLogs()).containsOnly(auditLogBack);
        assertThat(auditLogBack.getDocument()).isEqualTo(document);

        document.removeAuditLogs(auditLogBack);
        assertThat(document.getAuditLogs()).doesNotContain(auditLogBack);
        assertThat(auditLogBack.getDocument()).isNull();

        document.auditLogs(new HashSet<>(Set.of(auditLogBack)));
        assertThat(document.getAuditLogs()).containsOnly(auditLogBack);
        assertThat(auditLogBack.getDocument()).isEqualTo(document);

        document.setAuditLogs(new HashSet<>());
        assertThat(document.getAuditLogs()).doesNotContain(auditLogBack);
        assertThat(auditLogBack.getDocument()).isNull();
    }

    @Test
    void ownerTest() {
        Document document = getDocumentRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        document.setOwner(appUserBack);
        assertThat(document.getOwner()).isEqualTo(appUserBack);

        document.owner(null);
        assertThat(document.getOwner()).isNull();
    }
}
