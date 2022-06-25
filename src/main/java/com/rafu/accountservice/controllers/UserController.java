package com.rafu.accountservice.controllers;

import com.rafu.accountservice.mappers.UserMapper;
import com.rafu.accountservice.models.rest.UserRequest;
import com.rafu.accountservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private static final HttpStatus created = HttpStatus.CREATED;

    @Validated
    @PostMapping
    public ResponseEntity<URI> postUser(@RequestBody @Valid UserRequest request) {
        final var user = mapper.toUser(request);
        final var userSaved = service.save(user);

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSaved.getId())
                .toUri();

        return ResponseEntity.status(created).body(uri);
    }
}
