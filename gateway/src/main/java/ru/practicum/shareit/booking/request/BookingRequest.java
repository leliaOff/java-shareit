package ru.practicum.shareit.booking.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.base.helpers.DateTimeHelper;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class BookingRequest {
    private Long itemId;
    private String start;
    private String end;

    public BookingRequest validate() {
        checkDate();
        return this;
    }

    private void checkDate() {
        if (start == null) {
            throw new ValidationException("Необходимо указать дату начала аренды");
        }
        Instant startDate = DateTimeHelper.toInstant(start);
        if (startDate.isBefore(Instant.now())) {
            throw new ValidationException("Дата начала аренды не может быть раньше текущей даты");
        }
        if (end == null) {
            throw new ValidationException("Необходимо указать дату окончания аренды");
        }
        Instant endDate = DateTimeHelper.toInstant(end);
        if (endDate.isBefore(Instant.now())) {
            throw new ValidationException("Дата окончания аренды не может быть раньше текущей даты");
        }
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("Дата окончания аренды не может быть раньше даты начала аренды");
        }
    }
}
