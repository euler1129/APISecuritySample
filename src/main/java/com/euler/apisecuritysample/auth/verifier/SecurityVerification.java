package com.euler.apisecuritysample.auth.verifier;

import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import org.springframework.core.Ordered;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

public interface SecurityVerification extends Ordered {

    AuthenticateResult verify(RequestData requestData);

}
