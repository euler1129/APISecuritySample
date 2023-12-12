package com.euler.apisecuritysample.auth.verifier.impl;

import com.euler.apisecuritysample.auth.verifier.SecurityVerification;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ParametersVerification implements SecurityVerification {
    @Override
    public AuthenticateResult verify(RequestData requestData) {
        if(!this.checkHeader(requestData)){
            return AuthenticateResult.builder().pass(false).message("请传递正确的请求参数").build();
        }
        return AuthenticateResult.builder().pass(true).message("").build();
    }

    private boolean checkHeader(RequestData requestData){
        return Objects.nonNull(requestData.getAppId()) &&
                Objects.nonNull(requestData.getNonce()) &&
                Objects.nonNull(requestData.getTimestamp()) &&
                Objects.nonNull(requestData.getSign());

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
