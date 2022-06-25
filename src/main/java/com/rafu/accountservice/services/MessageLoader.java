package com.rafu.accountservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@PropertySource("classpath:messages/messages.properties")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageLoader {
    private static final Locale DEFAULT_LOCATION_BRAZIL = new Locale("pt", "BR");
    private final MessageSource messageSource;

    public String get(String key, Object... params) {
        return messageSource.getMessage(key, params, DEFAULT_LOCATION_BRAZIL);
    }

}
