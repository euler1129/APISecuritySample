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
public class AuthenticateResult {

    private Boolean pass;

    private String message;

}
