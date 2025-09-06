package com.mbbank.biz.pro.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SensitiveInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SensitiveInfo getSensitiveInfoSample1() {
        return new SensitiveInfo().id(1L).infoType("infoType1").pageNumber(1).position("position1");
    }

    public static SensitiveInfo getSensitiveInfoSample2() {
        return new SensitiveInfo().id(2L).infoType("infoType2").pageNumber(2).position("position2");
    }

    public static SensitiveInfo getSensitiveInfoRandomSampleGenerator() {
        return new SensitiveInfo()
            .id(longCount.incrementAndGet())
            .infoType(UUID.randomUUID().toString())
            .pageNumber(intCount.incrementAndGet())
            .position(UUID.randomUUID().toString());
    }
}
