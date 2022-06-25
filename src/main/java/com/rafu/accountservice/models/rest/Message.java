package com.rafu.accountservice.models.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rafu.accountservice.models.enums.MessageEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String text;
    private MessageEnum type;
    private Integer code;
    private List<String> details;
}
