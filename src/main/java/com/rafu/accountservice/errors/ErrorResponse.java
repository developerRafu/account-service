package com.rafu.accountservice.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private Integer code;
    private ErrorType errorType;
}
