package com.rafu.accountservice.services;

import com.rafu.accountservice.helpers.UserMockBuilder;
import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    User userMock;
    UserRepository repository;
    BCryptPasswordEncoder encoder;
    UserService service;

    @BeforeEach
    void setUp() {
        userMock = UserMockBuilder.getBuilder().mockDefaultValues().build();
        repository = mock(UserRepository.class);
        encoder = new BCryptPasswordEncoder();
        service = new UserService(repository, encoder);
    }

    @DisplayName("save method tests")
    @Nested
    class saveTests {
        @Test
        void should_return_a_valid_user() {
            when(repository.save(any())).thenReturn(userMock);
            final var result = service.save(userMock);
            assertNotNull(result);
        }

        @Test
        void should_call_repository() {
            service.save(userMock);
            verify(repository).save(userMock);
        }

        @Test
        void should_return_a_filled_entity() {
            when(repository.save(any())).thenReturn(userMock);
            final var result = service.save(userMock);
            assertNotNull(result);
            assertUser(userMock, result);
        }

        @Test
        void should_encrypt_user_password() {
            final var initialPassword = userMock.getPassword();
            when(repository.save(any())).thenReturn(userMock);
            final var result = service.save(userMock);
            assertNotEquals(initialPassword, result.getPassword());
        }
    }

    @DisplayName("Find by id test")
    @Nested
    class findByIdTests {
        @Test
        void should_return_an_user_when_find_one() {
            when(repository.findById(anyLong())).thenReturn(Optional.of(userMock));
            final var result = service.findById(1L);
            assertUser(userMock, result.get());
        }

        @Test
        void should_not_return_an_user_when_not_find_one() {
            final var result = service.findById(1L);
            assertTrue(result.isEmpty());
        }
    }

    @DisplayName("Find by email test")
    @Nested
    class findByEmailTests {
        @Test
        void should_return_an_user_when_find_one() {
            when(repository.findByEmail(anyString())).thenReturn(Optional.of(userMock));
            final var result = service.findByEmail("some@mail.com");
            assertUser(userMock, result.get());
        }

        @Test
        void should_not_return_an_user_when_not_find_one() {
            final var result = service.findByEmail(anyString());
            assertTrue(result.isEmpty());
        }
    }

    private void assertUser(User expected, User current) {
        assertEquals(expected.getId(), current.getId());
        assertEquals(expected.getEmail(), current.getEmail());
        assertEquals(expected.getPassword(), current.getPassword());
        assertEquals(expected.getName(), current.getName());
        assertEquals(expected.getProfile(), current.getProfile());
    }
}
