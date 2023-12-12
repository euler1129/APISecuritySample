package com.euler.apisecuritysample.auth.authenticator.impl;

import com.euler.apisecuritysample.auth.authenticator.ApiAuthenticator;
import com.euler.apisecuritysample.auth.verifier.impl.SecurityVerificationChain;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

public class PublicApiAuthenticator implements ApiAuthenticator {

    @Override
    public AuthenticateResult authenticate(ServerWebExchange serverWebExchange) {
        HttpHeaders httpHeaders = serverWebExchange.getRequest().getHeaders();
        RequestData requestData = RequestData.builder().build();
        if (httpHeaders.containsKey("AppId")) {
            requestData.setAppId(String.valueOf(httpHeaders.get("AppId")));
        }
        if (httpHeaders.containsKey("Nonce")) {
            requestData.setNonce(String.valueOf(httpHeaders.get("Nonce")));
        }
        if (httpHeaders.containsKey("Timestamp")) {
            requestData.setTimestamp(String.valueOf(httpHeaders.get("Timestamp")));
        }
        if (httpHeaders.containsKey("Sign")) {
            requestData.setSign(String.valueOf(httpHeaders.get("Sign")));
        }
        ApplicationContext applicationContext = serverWebExchange.getApplicationContext();
        SecurityVerificationChain securityVerificationChain = Objects.requireNonNull(applicationContext).getBean(SecurityVerificationChain.class);
        return securityVerificationChain.verify(requestData);
    }
}
