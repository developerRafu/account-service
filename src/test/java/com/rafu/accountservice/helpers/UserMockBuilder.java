package com.rafu.accountservice.helpers;

import com.rafu.accountservice.models.entities.User;
import com.rafu.accountservice.models.enums.Profile;

import static com.rafu.accountservice.models.enums.Profile.ADMIN;

public class UserMockBuilder {
    private final User user;
    private UserMockBuilder(){
        user = new User();
    }
    public static UserMockBuilder getBuilder(){
        return new UserMockBuilder();
    }
    public UserMockBuilder mockDefaultValues(){
        user.setId(1L);
        user.setName("Account Name");
        user.setEmail("account@mail.com");
        user.setPassword("$2a$10$/U7A3PnP.3qy2jAJYsqyfu5YAdjGrIDV59hk.DJn/XNwC3zEQxC4y");
        user.setProfile(ADMIN);
        return this;
    }
    public User build(){
        return user;
    }
}
