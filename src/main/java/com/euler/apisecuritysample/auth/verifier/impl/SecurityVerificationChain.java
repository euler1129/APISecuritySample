package com.euler.apisecuritysample.auth.verifier.impl;

import com.euler.apisecuritysample.auth.verifier.SecurityVerification;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@Component
public class SecurityVerificationChain {
    @Resource
    private List<SecurityVerification> securityVerificationList;

    public AuthenticateResult verify(RequestData requestData){
        for (SecurityVerification securityVerification : securityVerificationList){
            AuthenticateResult authenticateResult = securityVerification.verify(requestData);
            if(!authenticateResult.getPass()) return authenticateResult;
        }
        return AuthenticateResult.builder().pass(true).message("").build();
    }

}
