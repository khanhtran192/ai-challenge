package com.mbbank.biz.pro.service.mapper;

import static com.mbbank.biz.pro.domain.SensitiveInfoAsserts.*;
import static com.mbbank.biz.pro.domain.SensitiveInfoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SensitiveInfoMapperTest {

    private SensitiveInfoMapper sensitiveInfoMapper;

    @BeforeEach
    void setUp() {
        sensitiveInfoMapper = new SensitiveInfoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSensitiveInfoSample1();
        var actual = sensitiveInfoMapper.toEntity(sensitiveInfoMapper.toDto(expected));
        assertSensitiveInfoAllPropertiesEquals(expected, actual);
    }
}
