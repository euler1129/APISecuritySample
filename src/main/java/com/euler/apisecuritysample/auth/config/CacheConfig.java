package com.euler.apisecuritysample.auth.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    private static final long EXPIRE_TIME = 60;

    @Bean
    public Cache<String, Object> caffeineCache(){
        return Caffeine.newBuilder()
                .expireAfterWrite(EXPIRE_TIME, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(1000)
                .build();
    }

}
