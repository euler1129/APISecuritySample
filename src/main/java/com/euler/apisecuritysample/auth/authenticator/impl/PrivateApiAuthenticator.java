package com.euler.apisecuritysample.auth.authenticator.impl;

import com.euler.apisecuritysample.auth.authenticator.ApiAuthenticator;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import org.springframework.web.server.ServerWebExchange;

public class PrivateApiAuthenticator implements ApiAuthenticator {
    @Override
    public AuthenticateResult authenticate(ServerWebExchange serverWebExchange) {
        return null;
    }
}
