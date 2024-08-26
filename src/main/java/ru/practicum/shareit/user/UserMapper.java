package ru.practicum.shareit.user;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
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
