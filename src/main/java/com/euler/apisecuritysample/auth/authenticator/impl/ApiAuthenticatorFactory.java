package com.euler.apisecuritysample.auth.authenticator.impl;

import com.euler.apisecuritysample.auth.authenticator.ApiAuthenticator;

import java.util.HashMap;
import java.util.Map;

public class ApiAuthenticatorFactory {

    private static final String PUBLIC_PATH = "pub";
    private static final String PROTECTED_PATH = "pro";
    private static final String PRIVATE_PATH = "pri";
    private static final String DEFAULT_PATH = "def";

    private static final Map<String, ApiAuthenticator> apiAuthentiatorMap = new HashMap<>();

    static {
        apiAuthentiatorMap.put(PUBLIC_PATH, new PublicApiAuthenticator());
        apiAuthentiatorMap.put(PROTECTED_PATH, new ProtectedApiAuthenticator());
        apiAuthentiatorMap.put(PRIVATE_PATH, new PrivateApiAuthenticator());
        apiAuthentiatorMap.put(DEFAULT_PATH, new DefaultApiAuthenticator());
    }

    public static ApiAuthenticator getApiAuthenticator(String path){
        String[] parts = path.split("/");
        if (parts.length < 2) return new DefaultApiAuthenticator();
        String p2 = parts[1];
        if(!apiAuthentiatorMap.containsKey(p2)) throw new IllegalStateException("Unexpected value: " + p2);
        return apiAuthentiatorMap.get(p2);
    }

}
