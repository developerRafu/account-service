package com.rafu.accountservice.services;

import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.models.enums.Profile;
import com.rafu.accountservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User save(final User user) {
        encodePassword(user);
        setUserProfile(user);
        repository.save(user);
        return user;
    }

    public Optional<User> findById(final Long id){
        return repository.findById(id);
    }

    public Optional<User> findByEmail(final String email){
        return repository.findByEmail(email);
    }

    private void setUserProfile(final User user) {
        user.setProfile(Profile.USER);
    }

    private void encodePassword(final User user) {
        final var encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
