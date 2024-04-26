package com.ggmaciel.orlaapi.config;

import static java.time.temporal.ChronoUnit.SECONDS;

import com.ggmaciel.orlaapi.exceptions.ApiException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class Resilience4JConfig {

    public Retry getRetry(String retryName, int maxAttempts, int waitDuration) {
        var config = RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(Duration.of(waitDuration, SECONDS))
                .retryExceptions(ApiException.class)
                .build();
        var retryRegistry = RetryRegistry.ofDefaults();
        return retryRegistry.retry(retryName, config);
    }
}
