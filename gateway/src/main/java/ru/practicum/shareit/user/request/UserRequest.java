package ru.practicum.shareit.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.base.exception.ValidationException;

import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private String email;
    private String name;

    public UserRequest validate() {
        checkEmail(email);
        return this;
    }

    private void checkEmail(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new ValidationException("Указан невалидный адрес электронной почты");
        }
    }
}
