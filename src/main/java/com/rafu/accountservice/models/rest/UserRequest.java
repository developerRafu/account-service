package com.rafu.accountservice.models.rest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequest {
    @Size(min = 8, max = 50, message = "Invalid name.")
    private String name;
    @Email(message = "Invalid email.")
    private String email;
    @Size(min = 8, max = 30,message = "Invalid password.")
    private String password;
}
