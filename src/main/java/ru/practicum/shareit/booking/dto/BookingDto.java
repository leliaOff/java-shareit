package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

@Getter
@Setter
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String start;
    private String end;
    private BookingStatus status;
    private String review;
    private UserDto booker;
    private ItemDto item;
}
