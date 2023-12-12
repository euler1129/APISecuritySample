package com.euler.apisecuritysample.auth.authenticator.impl;

import com.euler.apisecuritysample.auth.authenticator.ApiAuthenticator;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import org.springframework.web.server.ServerWebExchange;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

public class DefaultApiAuthenticator implements ApiAuthenticator {
    @Override
    public AuthenticateResult authenticate(ServerWebExchange serverWebExchange) {
        return null;
    }
}
