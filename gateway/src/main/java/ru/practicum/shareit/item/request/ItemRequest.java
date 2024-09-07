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
public class ItemRequest {
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;

    public ItemRequest validate() {
        checkName();
        checkDescription();
        checkAvailable();
        return this;
    }

    protected void checkName() {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Необходимо указать наименование вещи");
        }
    }

    protected void checkDescription() {
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Необходимо указать описание запроса");
        }
    }

    protected void checkAvailable() {
        if (available == null) {
            throw new ValidationException("Необходимо указать доступность вещи");
        }
    }
}
