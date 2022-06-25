package com.rafu.accountservice.models.rest;

import com.rafu.accountservice.models.enums.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Profile profile;
}
