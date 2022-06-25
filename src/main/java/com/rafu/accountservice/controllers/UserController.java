package com.rafu.accountservice.controllers;

import com.rafu.accountservice.errors.NotFoundException;
import com.rafu.accountservice.mappers.UserMapper;
import com.rafu.accountservice.models.rest.UserRequest;
import com.rafu.accountservice.models.rest.UserResponse;
import com.rafu.accountservice.services.AuthService;
import com.rafu.accountservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private final AuthService authService;
    private static final HttpStatus created = HttpStatus.CREATED;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@RequestHeader @NotNull final String authorization, @PathVariable @Valid @NotNull Long id) {
        authService.isAuthorized(authorization);
        return service
                .findById(id)
                .map(user -> ResponseEntity.ok(mapper.toResponse(user)))
                .orElseThrow(() -> new NotFoundException("User"));
    }
}
