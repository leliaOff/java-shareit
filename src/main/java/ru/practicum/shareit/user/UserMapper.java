package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
            user.getId(),
            user.getEmail(),
            user.getName()
        );
    }

    public static User toModel(UserDto user) {
        User model = new User();
        model.setId(user.getId());
        model.setEmail(user.getEmail());
        model.setName(user.getName());
        return model;
    }

    public static User mergeToModel(User model, UserDto user) {
        if (user.getEmail() != null) {
            model.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            model.setName(user.getName());
        }
        return model;
    }
}
