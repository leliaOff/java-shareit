package ru.practicum.shareit.helpers;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.enums.BookingStatus;

public class BookingHelper {

    public static RequestBookingDto getRequestBookingDto() {
        return new RequestBookingDto(1L,
                "2024-09-01T10:00:00",
                "2024-09-02T10:00:00");
    }

    public static BookingDto getBookingDto() {
        return new BookingDto(1L,
                "2024-09-01T10:00:00",
                "2024-09-02T10:00:00",
                BookingStatus.WAITING,
                null,
                UserHelper.getUserDto(),
                ItemHelper.getItemDto()
        );
    }
}
