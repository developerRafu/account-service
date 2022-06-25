package com.rafu.accountservice.models.rest;

import com.rafu.accountservice.models.enums.MessageEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String text;
    private MessageEnum type;
    private Integer code;
    private List<String> details;
}
