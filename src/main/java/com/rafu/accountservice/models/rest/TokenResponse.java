package com.rafu.accountservice.models.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String type;
    private String token;
    private Long duration;
}
