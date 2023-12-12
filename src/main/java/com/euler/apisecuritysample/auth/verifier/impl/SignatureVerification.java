package com.euler.apisecuritysample.auth.verifier.impl;

import com.euler.apisecuritysample.auth.tools.GeneralTools;
import com.euler.apisecuritysample.auth.verifier.SecurityVerification;
import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@Slf4j
@Component
public class SignatureVerification implements SecurityVerification {
    @Override
    public AuthenticateResult verify(RequestData requestData) {
        String serverSign = this.sign(requestData);
        String clientSign = requestData.getSign().substring(1, requestData.getSign().length() - 1);
        if(!Objects.equals(serverSign, clientSign)){
            return AuthenticateResult.builder().pass(false).message("签名无效").build();
        }
        return AuthenticateResult.builder().pass(true).message("").build();
    }

    private String sign(RequestData requestData){
        String appId = requestData.getAppId().substring(1, requestData.getAppId().length() - 1);
        String appSecret = GeneralTools.generateAppSecret(appId);
        StringBuilder sb = new StringBuilder();
        sb.append("AppId=")
            .append(appId)
            .append(",Nonce=")
            .append(requestData.getNonce().substring(1, requestData.getNonce().length() - 1))
            .append(",Timestamp=")
            .append(requestData.getTimestamp().substring(1, requestData.getTimestamp().length() - 1))
            .append(",AppSecret=")
            .append(appSecret);
        log.info("Server Sign String: {}", sb);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
