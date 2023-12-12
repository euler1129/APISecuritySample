package com.euler.apisecuritysample.auth.verifier.impl;

import com.euler.apisecuritysample.auth.verifier.SecurityVerification;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class NonceVerification implements SecurityVerification {

    private static final String NONCE_KEY_PREFIX = "x-nonce-";

    private final Cache<String, Object> caffeineCache;

    @Autowired
    public NonceVerification(Cache<String, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    @Override
    public AuthenticateResult verify(RequestData requestData) {
        String key = NONCE_KEY_PREFIX + requestData.getNonce();
        Object existsNonce = this.caffeineCache.get(key, v -> null);
        if(Objects.nonNull(existsNonce)){
            return AuthenticateResult.builder().pass(false).message("请不要重复提交请求").build();
        }
        this.caffeineCache.put(key, requestData.getNonce());
        return AuthenticateResult.builder().pass(true).message("").build();
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
