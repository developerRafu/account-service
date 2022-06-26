package com.rafu.accountservice.helpers;

import com.rafu.accountservice.models.rest.TokenResponse;

public class TokenMockBuilder {
    private final TokenResponse tokenResponse;

    private TokenMockBuilder() {
        tokenResponse = new TokenResponse();
    }

    public static TokenMockBuilder getBuilder() {
        return new TokenMockBuilder();
    }

    public TokenResponse build() {
        return tokenResponse;
    }

    public TokenMockBuilder mockDefaultValues() {
        tokenResponse.setToken(ConstantsHelper.MOCKED_TOKEN_WITHOUT_TYPE);
        tokenResponse.setDuration(ConstantsHelper.MOCKED_DURATION);
        tokenResponse.setType("Bearer");
        return this;
    }
}
