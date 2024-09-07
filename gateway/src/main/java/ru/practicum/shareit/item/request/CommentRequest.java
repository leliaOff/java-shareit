package ru.practicum.shareit.item.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.base.exception.ValidationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String text;

    public CommentRequest validate() {
        checkText();
        return this;
    }

    protected void checkText() {
        if (text == null || text.isEmpty()) {
            throw new ValidationException("Текст комментария не может быть пустым");
        }
    }
}
