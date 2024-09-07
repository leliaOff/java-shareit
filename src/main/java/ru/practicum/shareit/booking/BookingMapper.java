package ru.practicum.shareit.booking;

import ru.practicum.shareit.base.helpers.DateTimeHelper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.UserMapper;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                DateTimeHelper.toString(booking.getStart()),
                DateTimeHelper.toString(booking.getEnd()),
                booking.getStatus(),
                booking.getReview(),
                UserMapper.toDto(booking.getUser()),
                ItemMapper.toDto(booking.getItem())
        );
    }

    public static Booking requestToModel(RequestBookingDto dto) {
        Booking model = new Booking();
        model.setId(dto.getId());
        model.setStart(DateTimeHelper.toInstant(dto.getStart()));
        model.setEnd(DateTimeHelper.toInstant(dto.getEnd()));
        model.setStatus(dto.getStatus());
        model.setReview(dto.getReview());
        model.setStatus(dto.getStatus() == null ? BookingStatus.WAITING : dto.getStatus());
        return model;
    }

    public static Booking toModel(BookingDto dto) {
        Booking model = new Booking();
        model.setId(dto.getId());
        model.setUser(UserMapper.toModel(dto.getBooker()));
        model.setItem(ItemMapper.toModel(dto.getItem()));
        model.setStart(DateTimeHelper.toInstant(dto.getStart()));
        model.setEnd(DateTimeHelper.toInstant(dto.getEnd()));
        model.setStatus(dto.getStatus());
        model.setReview(dto.getReview());
        model.setStatus(dto.getStatus() == null ? BookingStatus.WAITING : dto.getStatus());
        return model;
    }
}
