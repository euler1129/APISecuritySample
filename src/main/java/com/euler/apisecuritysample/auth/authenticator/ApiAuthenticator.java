package com.euler.apisecuritysample.auth.authenticator;

import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import org.springframework.web.server.ServerWebExchange;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

public interface ApiAuthenticator {

    AuthenticateResult authenticate(ServerWebExchange serverWebExchange);

}
