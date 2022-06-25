package com.rafu.accountservice.mappers;

import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.models.rest.UserRequest;
import com.rafu.accountservice.models.rest.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(final UserRequest request);

    UserResponse toResponse(User user);
}
