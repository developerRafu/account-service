package com.rafu.accountservice.helpers;

import com.rafu.accountservice.models.rest.UserLoginRequest;

public class UserLoginMockBuilder {
    private final UserLoginRequest request;

    private UserLoginMockBuilder() {
        this.request = new UserLoginRequest();
    }

    public static UserLoginMockBuilder getBuilder() {
        return new UserLoginMockBuilder();
    }

    public UserLoginMockBuilder mockDefaultValues() {
        request.setEmail(ConstantsHelper.MOCKED_SUBJECT);
        request.setPassword(ConstantsHelper.MOCKED_PASSWORD);
        return this;
    }

    public UserLoginRequest build() {
        return request;
    }
}
