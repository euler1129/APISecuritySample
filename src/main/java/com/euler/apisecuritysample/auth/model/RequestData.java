package com.euler.apisecuritysample.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
