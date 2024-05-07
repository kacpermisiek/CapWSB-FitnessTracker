package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    UserIdAndNameDto toIdAndNameDto(User user) {
        return new UserIdAndNameDto(user.getId(),
                                    user.getFirstName(),
                                    user.getLastName());
    }

    UserIdAndEmailDto toIdAndEmailDto(User user) {
        return new UserIdAndEmailDto(user.getId(),
                                    user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

}
