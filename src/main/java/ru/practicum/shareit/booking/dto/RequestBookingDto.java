package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.enums.BookingStatus;

@Getter
@Setter
@AllArgsConstructor
public class RequestBookingDto {
    private Long id;
    private Long itemId;
    private Long userId;
    private String start;
    private String end;
    private BookingStatus status;
    private String review;
}
