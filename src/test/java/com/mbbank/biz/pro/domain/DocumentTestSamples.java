package com.mbbank.biz.pro.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Document getDocumentSample1() {
        return new Document().id(1L).fileName("fileName1").fileSize(1L);
    }

    public static Document getDocumentSample2() {
        return new Document().id(2L).fileName("fileName2").fileSize(2L);
    }

    public static Document getDocumentRandomSampleGenerator() {
        return new Document().id(longCount.incrementAndGet()).fileName(UUID.randomUUID().toString()).fileSize(longCount.incrementAndGet());
    }
}
