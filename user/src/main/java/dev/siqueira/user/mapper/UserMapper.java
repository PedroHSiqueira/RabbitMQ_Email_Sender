package dev.siqueira.user.mapper;

import dev.siqueira.user.dtos.UserDto;
import dev.siqueira.user.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());

        return user;
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getName(),  user.getEmail());
    }

}
