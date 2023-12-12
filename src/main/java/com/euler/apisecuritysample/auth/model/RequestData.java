package com.euler.apisecuritysample.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestData {

    private String appId;
    private String nonce;
    private String timestamp;
    private String sign;

}
