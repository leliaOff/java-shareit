package ru.practicum.shareit.request.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.base.exception.ValidationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRequest {
    private String description;

    public RequestRequest validate() {
        checkDescription();
        return this;
    }

    private void checkDescription() {
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Необходимо указать описание запроса");
        }
    }
}
