package com.rafu.accountservice.errors;

import com.rafu.accountservice.models.enums.MessageEnum;
import com.rafu.accountservice.models.rest.Message;
import com.rafu.accountservice.services.MessageLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountExceptionHandlerV1 {
    private final MessageLoader messageLoader;

    private static final String BAD_FIELD = "badFields";
    private static final String NOT_FOUND = "notFound";
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex, final HttpServletRequest request) {
        final var message = new Message();
        final var text = messageLoader.get(BAD_FIELD, ex.getFieldError());
        message.setText(text);
        message.setType(MessageEnum.ERROR);
        message.setCode(BAD_REQUEST.value());
        message.setDetails(getFields(ex));
        return message;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Message handleNotFoundException(final NotFoundException ex, final HttpServletRequest request) {
        final var message = new Message();
        final var text = messageLoader.get(NOT_FOUND, ex.getMessage());
        message.setText(text);
        message.setType(MessageEnum.ERROR);
        message.setCode(HttpStatus.NOT_FOUND.value());
        return message;
    }

    private List<String> getFields(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    }
}
