package com.mbbank.biz.pro.domain;

import static com.mbbank.biz.pro.domain.AppUserTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentShareTestSamples.*;
import static com.mbbank.biz.pro.domain.DocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mbbank.biz.pro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentShareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentShare.class);
        DocumentShare documentShare1 = getDocumentShareSample1();
        DocumentShare documentShare2 = new DocumentShare();
        assertThat(documentShare1).isNotEqualTo(documentShare2);

        documentShare2.setId(documentShare1.getId());
        assertThat(documentShare1).isEqualTo(documentShare2);

        documentShare2 = getDocumentShareSample2();
        assertThat(documentShare1).isNotEqualTo(documentShare2);
    }

    @Test
    void documentTest() {
        DocumentShare documentShare = getDocumentShareRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        documentShare.setDocument(documentBack);
        assertThat(documentShare.getDocument()).isEqualTo(documentBack);

        documentShare.document(null);
        assertThat(documentShare.getDocument()).isNull();
    }

    @Test
    void userTest() {
        DocumentShare documentShare = getDocumentShareRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        documentShare.setUser(appUserBack);
        assertThat(documentShare.getUser()).isEqualTo(appUserBack);

        documentShare.user(null);
        assertThat(documentShare.getUser()).isNull();
    }
}
