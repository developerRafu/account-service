package com.rafu.accountservice.controllers;

import com.rafu.accountservice.models.enums.MessageEnum;
import com.rafu.accountservice.models.rest.Message;
import com.rafu.accountservice.models.rest.TokenResponse;
import com.rafu.accountservice.models.rest.UserLoginRequest;
import com.rafu.accountservice.services.AuthService;
import com.rafu.accountservice.services.MessageLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {
    private final AuthService authService;
    private final MessageLoader loader;

    @GetMapping
    public ResponseEntity<TokenResponse> getAuth(@RequestHeader @Valid @NotNull final String authorization) {
        final var tokenResponse = authService.renewToken(authorization);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenResponse);
    }

    @PostMapping
    public ResponseEntity<Message> postLogin(@RequestBody UserLoginRequest request) {
        final var token = authService.login(request);
        final var message = getMessageAuthorized();

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        message.setDetails(List.of(uri.toString()));

        message.setAuth(token);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
    }

    private Message getMessageAuthorized() {
        final var message = new Message();
        message.setText(loader.get("userAuthorized"));
        message.setType(MessageEnum.SUCCESS);
        message.setCode(HttpStatus.ACCEPTED.value());
        return message;
    }
}
