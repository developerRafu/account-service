package com.rafu.accountservice.services;

import com.rafu.accountservice.errors.InvalidTokenException;
import com.rafu.accountservice.helpers.ConstantsHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.rafu.accountservice.helpers.ConstantsHelper.MOCKED_TOKEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class JWTServiceTest {
    JWTService service;
    private final static Long duration = 86400000L;

    @BeforeEach
    void setUp() {
        service = new JWTService();
        setField(service, "secret", "segredodorafu");
        setField(service, "expiration", duration);
    }

    @DisplayName("Get Token Tests")
    @Nested
    class getTokenTests {
        @Test
        void should_return_a_valid_token() {
            final var result = service.getToken("mail@mail.com");
            assertNotNull(result);
            assertEquals(duration, result.getDuration());
            assertEquals("Bearer", result.getType());
            assertNotNull(result.getToken());
        }
    }

    @DisplayName("is valid token tests")
    @Nested
    class isValidTokenTests {
        @Test
        void should_return_true_when_token_is_valid() {
            final var result = service.isValidToken(MOCKED_TOKEN);
            assertTrue(result);
        }

        @Test
        void should_throw_exception_when_token_is_invalid() {
            assertThrows(InvalidTokenException.class, () -> service.isValidToken("Bearer token"));
        }
    }

    @DisplayName("is valid token tests")
    @Nested
    class isExpiredTokenTests {
        @Test
        void should_return_true_when_token_is_expired() {
            final var result = service.isExpiredToken(MOCKED_TOKEN);
            assertTrue(result);
        }

        @Test
        void should_throw_exception_when_token_is_invalid() {
            assertThrows(InvalidTokenException.class, () -> service.isValidToken("Bearer token"));
        }
    }

    @DisplayName("Get Username Tests")
    @Nested
    class GetUsernameTests {
        @Test
        void should_return_a_valid_email() {
            final var result = service.getUsername(MOCKED_TOKEN);
            assertEquals("mail@mail.com", result);
        }

        @Test
        void should_return_exception_when_token_is_invalid() {
            assertThrows(InvalidTokenException.class, () -> service.getUsername("Bearer token"));
        }
    }
}
