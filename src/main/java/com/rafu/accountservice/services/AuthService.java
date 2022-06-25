package com.rafu.accountservice.services;

import com.rafu.accountservice.errors.NotFoundException;
import com.rafu.accountservice.errors.UnauthorizedException;
import com.rafu.accountservice.errors.WrongPasswordException;
import com.rafu.accountservice.models.rest.TokenResponse;
import com.rafu.accountservice.models.rest.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final JWTService jwtService;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public void isAuthorized(final String token) {
        final var claims = jwtService.getClaims(token);
        if (jwtService.isExpiredToken(claims)) {
            throw new UnauthorizedException(claims.getSubject());
        }
    }

    public TokenResponse renewToken(final String token) {
        final var claims = jwtService.getClaims(token);
        if (jwtService.isExpiredToken(claims)) {
            throw new UnauthorizedException(claims.getSubject());
        }
        return jwtService.getToken(claims.getSubject());
    }

    public TokenResponse login(UserLoginRequest request) {
        final var user = userService.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User"));
        if (!encoder.matches(request.getPassword(), user.getPassword())){
            throw new WrongPasswordException(request.getEmail());
        }
        return jwtService.getToken(user.getEmail());
    }
}
