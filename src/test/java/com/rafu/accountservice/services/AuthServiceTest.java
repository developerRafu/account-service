package com.rafu.accountservice.services;

import com.rafu.accountservice.errors.NotFoundException;
import com.rafu.accountservice.errors.UnauthorizedException;
import com.rafu.accountservice.errors.WrongPasswordException;
import com.rafu.accountservice.helpers.*;
import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.models.rest.TokenResponse;
import com.rafu.accountservice.models.rest.UserLoginRequest;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    AuthService service;
    UserService userService;
    JWTService jwtService;
    BCryptPasswordEncoder encoder;
    Claims claimsMocked;
    TokenResponse tokenResponse;
    UserLoginRequest request;
    User user;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtService = mock(JWTService.class);
        encoder = new BCryptPasswordEncoder();
        service = new AuthService(jwtService, userService, encoder);
        claimsMocked = ClaimsMockBuilder.getBuilder().mockDefaultValues().build();
        tokenResponse = TokenMockBuilder.getBuilder().mockDefaultValues().build();
        request = UserLoginMockBuilder.getBuilder().mockDefaultValues().build();
        user = UserMockBuilder.getBuilder().mockDefaultValues().build();
    }

    @DisplayName("Is Authorized Tests")
    @Nested
    class isAuthorizedTests {
        @Test
        void should_not_throw_exception_when_it_is_authorized() {
            when(jwtService.getClaims(anyString())).thenReturn(claimsMocked);
            assertDoesNotThrow(() -> service.isAuthorized(ConstantsHelper.MOCKED_TOKEN));
        }

        @Test
        void should_throw_exception_when_it_is_not_authorized() {
            when(jwtService.getClaims(anyString())).thenReturn(claimsMocked);
            when(jwtService.isExpiredToken(any(Claims.class))).thenReturn(Boolean.TRUE);
            assertThrows(UnauthorizedException.class, () -> service.isAuthorized(ConstantsHelper.MOCKED_TOKEN));
        }
    }

    @DisplayName("Renew Token Tests")
    @Nested
    class renewTokenTests {
        @Test
        void should_return_a_new_token() {
            when(jwtService.getClaims(anyString())).thenReturn(claimsMocked);
            when(jwtService.getToken(anyString())).thenReturn(tokenResponse);
            final var result = service.renewToken(ConstantsHelper.MOCKED_TOKEN);
            assertToken(result);
        }

        @Test
        void should_throw_exception_when_it_is_not_authorized() {
            when(jwtService.getClaims(anyString())).thenReturn(claimsMocked);
            when(jwtService.isExpiredToken(any(Claims.class))).thenReturn(Boolean.TRUE);
            assertThrows(UnauthorizedException.class, () -> service.renewToken(ConstantsHelper.MOCKED_TOKEN));
        }
    }

    @DisplayName("Login Tests")
    @Nested
    class LoginTests {
        @Test
        void should_return_a_valid_token_response() {
            when(userService.findByEmail(anyString())).thenReturn(Optional.of(user));
            when(jwtService.getToken(anyString())).thenReturn(tokenResponse);
            final var result = service.login(request);
            assertToken(result);
        }

        @Test
        void should_throw_exception_when_user_does_not_exists() {
            when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> service.login(request));
        }

        @Test
        void should_throw_exception_when_password_it_is_wrong() {
            request.setPassword("1");
            when(userService.findByEmail(anyString())).thenReturn(Optional.of(user));
            when(jwtService.getToken(anyString())).thenReturn(tokenResponse);
            assertThrows(WrongPasswordException.class, () -> service.login(request));
        }
    }

    private void assertToken(TokenResponse result) {
        assertNotNull(result);
        assertNotNull(result.getToken());
        assertNotNull(result.getType());
        assertNotNull(result.getDuration());
    }
}
