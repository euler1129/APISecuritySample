package com.euler.apisecuritysample.auth.filter;

import com.euler.apisecuritysample.auth.authenticator.ApiAuthenticator;
import com.euler.apisecuritysample.auth.authenticator.impl.ApiAuthenticatorFactory;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Arrays;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@Component
public class ApiAuthenticateFilter implements GlobalFilter, Ordered {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String pathValue = exchange.getRequest().getPath().value();
        ApiAuthenticator apiAuthenticator = ApiAuthenticatorFactory.getApiAuthenticator(pathValue);
        AuthenticateResult authenticateResult = apiAuthenticator.authenticate(exchange);
        if (!authenticateResult.getPass()){
            //return Mono.error(new HttpServerErrorException(HttpStatus.METHOD_NOT_ALLOWED, authenticateResult.getMessage()));
            try {
                ServerHttpResponse serverHttpResponse = exchange.getResponse();
                serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return serverHttpResponse.writeWith(Flux.just(serverHttpResponse.bufferFactory().wrap(objectMapper.writeValueAsBytes(authenticateResult))));
            } catch (JsonProcessingException e) {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.toString(e.getStackTrace()));
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
