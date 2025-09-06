package com.mbbank.biz.pro.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentShareTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DocumentShare getDocumentShareSample1() {
        return new DocumentShare().id(1L);
    }

    public static DocumentShare getDocumentShareSample2() {
        return new DocumentShare().id(2L);
    }

    public static DocumentShare getDocumentShareRandomSampleGenerator() {
        return new DocumentShare().id(longCount.incrementAndGet());
    }
}
