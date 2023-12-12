package com.euler.apisecuritysample.auth.verifier;

import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import org.springframework.core.Ordered;

public interface SecurityVerification extends Ordered {

    AuthenticateResult verify(RequestData requestData);

}
