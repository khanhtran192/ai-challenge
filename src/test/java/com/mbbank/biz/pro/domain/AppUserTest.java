package com.mbbank.biz.pro.domain;

import static com.mbbank.biz.pro.domain.AppUserTestSamples.*;
import static com.mbbank.biz.pro.domain.AuditLogTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentShareTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void documentsTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        appUser.addDocuments(documentBack);
        assertThat(appUser.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getOwner()).isEqualTo(appUser);

        appUser.removeDocuments(documentBack);
        assertThat(appUser.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getOwner()).isNull();

        appUser.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(appUser.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getOwner()).isEqualTo(appUser);

        appUser.setDocuments(new HashSet<>());
        assertThat(appUser.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getOwner()).isNull();
    }

    @Test
    void sharesTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        DocumentShare documentShareBack = getDocumentShareRandomSampleGenerator();

        appUser.addShares(documentShareBack);
        assertThat(appUser.getShares()).containsOnly(documentShareBack);
        assertThat(documentShareBack.getUser()).isEqualTo(appUser);

        appUser.removeShares(documentShareBack);
        assertThat(appUser.getShares()).doesNotContain(documentShareBack);
        assertThat(documentShareBack.getUser()).isNull();

        appUser.shares(new HashSet<>(Set.of(documentShareBack)));
        assertThat(appUser.getShares()).containsOnly(documentShareBack);
        assertThat(documentShareBack.getUser()).isEqualTo(appUser);

        appUser.setShares(new HashSet<>());
        assertThat(appUser.getShares()).doesNotContain(documentShareBack);
        assertThat(documentShareBack.getUser()).isNull();
    }

    @Test
    void auditLogsTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        AuditLog auditLogBack = getAuditLogRandomSampleGenerator();

        appUser.addAuditLogs(auditLogBack);
        assertThat(appUser.getAuditLogs()).containsOnly(auditLogBack);
        assertThat(auditLogBack.getUser()).isEqualTo(appUser);

        appUser.removeAuditLogs(auditLogBack);
        assertThat(appUser.getAuditLogs()).doesNotContain(auditLogBack);
        assertThat(auditLogBack.getUser()).isNull();

        appUser.auditLogs(new HashSet<>(Set.of(auditLogBack)));
        assertThat(appUser.getAuditLogs()).containsOnly(auditLogBack);
        assertThat(auditLogBack.getUser()).isEqualTo(appUser);

        appUser.setAuditLogs(new HashSet<>());
        assertThat(appUser.getAuditLogs()).doesNotContain(auditLogBack);
        assertThat(auditLogBack.getUser()).isNull();
    }
}
