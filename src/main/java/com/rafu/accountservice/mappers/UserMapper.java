package com.rafu.accountservice.mappers;

import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.models.rest.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(final UserRequest request);
}
