package com.ecommerce.order_server.mapper;

import com.ecommerce.order_server.dto.UserDto;
import com.ecommerce.order_server.entity.User;

public class UserMapper {
    // Convert User entity to UserDto
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Convert UserDto to User entity
    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
