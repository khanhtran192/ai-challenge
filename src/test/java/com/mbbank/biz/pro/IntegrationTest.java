package com.mbbank.biz.pro;

import com.mbbank.biz.pro.config.AsyncSyncConfiguration;
import com.mbbank.biz.pro.config.EmbeddedRedis;
import com.mbbank.biz.pro.config.EmbeddedSQL;
import com.mbbank.biz.pro.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { AiChallengeApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
