package com.euler.apisecuritysample.auth.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@Slf4j
public class GeneralToolsTest {

    @Test
    public void generateAppSecret() {
        String appId = "AppId=Spq5IjZgyO,Nonce=kafuruspr,Timestamp=2023-12-11 20:59:19.533,AppSecret=0141E437B4F2C16B0B0EEF64BF7A8881";
        String clientSecret = GeneralTools.generateAppSecret(appId);
        log.info("Client Secret: {}", clientSecret);
        String serverSecret = GeneralTools.generateAppSecret(appId);
        log.info("Server Secret: {}", serverSecret);
        Assert.assertEquals(clientSecret, serverSecret);
    }

}