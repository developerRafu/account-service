package com.rafu.accountservice.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

import java.time.Instant;
import java.util.Date;

public class ClaimsMockBuilder {
    private final Claims claims;

    private ClaimsMockBuilder() {
        this.claims = new DefaultClaims();
    }

    public static ClaimsMockBuilder getBuilder() {
        return new ClaimsMockBuilder();
    }

    public Claims build() {
        return claims;
    }

    public ClaimsMockBuilder mockDefaultValues(){
        claims.setExpiration(Date.from(Instant.ofEpochMilli(ConstantsHelper.MOCKED_DURATION)));
        claims.setSubject(ConstantsHelper.MOCKED_SUBJECT);
        return this;
    }
}
