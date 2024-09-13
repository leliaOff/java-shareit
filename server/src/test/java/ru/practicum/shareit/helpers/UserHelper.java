package ru.practicum.shareit.helpers;

import ru.practicum.shareit.user.dto.UserDto;

public class UserHelper {

    public static UserDto getUserDto() {
        return new UserDto(1L, "user@mail.ru", "User Test");
    }

    public static UserDto getUserDto(String email, String name) {
        return new UserDto(
                null,
                email,
                name
        );
    }

    public static UserDto getUserDto(Long id, String email, String name) {
        return new UserDto(
                id,
                email,
                name
        );
    }
}
