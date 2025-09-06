package com.mbbank.biz.pro.service.mapper;

import static com.mbbank.biz.pro.domain.DocumentShareAsserts.*;
import static com.mbbank.biz.pro.domain.DocumentShareTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentShareMapperTest {

    private DocumentShareMapper documentShareMapper;

    @BeforeEach
    void setUp() {
        documentShareMapper = new DocumentShareMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentShareSample1();
        var actual = documentShareMapper.toEntity(documentShareMapper.toDto(expected));
        assertDocumentShareAllPropertiesEquals(expected, actual);
    }
}
