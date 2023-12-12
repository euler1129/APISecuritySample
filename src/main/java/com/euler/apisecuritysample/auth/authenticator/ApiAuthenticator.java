package com.euler.apisecuritysample.auth.authenticator;

import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import org.springframework.web.server.ServerWebExchange;

public interface ApiAuthenticator {

    AuthenticateResult authenticate(ServerWebExchange serverWebExchange);

}
