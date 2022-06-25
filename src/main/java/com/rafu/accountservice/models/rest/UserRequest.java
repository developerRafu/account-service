package com.rafu.accountservice.models.rest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Getter
@Setter
public class UserRequest {
    @Min(value = 5, message = "nome deve conter, pelo menos, 5 caracteres")
    private String name;
    @Email(message = "preencha com um email válido")
    private String email;
    @Min(value = 8, message = "senha deve conter, pelo menos, 8 caracteres")
    private String password;
}
