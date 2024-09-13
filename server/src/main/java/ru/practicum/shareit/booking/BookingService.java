package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.enums.BookingFilterState;
import ru.practicum.shareit.booking.storage.BookingStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {
    @Qualifier("dbBookingStorage")
    private final BookingStorage storage;

    private final BookingValidator bookingValidator;

    @Autowired
    BookingService(@Qualifier("dbBookingStorage") BookingStorage storage,
                   BookingValidator bookingValidator) {
        this.storage = storage;
        this.bookingValidator = bookingValidator;
    }

    public Collection<BookingDto> getAllBookings(Long userId, BookingFilterState state) {
        return storage
                .getAllBookings(userId, state)
                .stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<BookingDto> getAllBookingsByOwner(Long ownerId, BookingFilterState state) {
        bookingValidator.checkAndGetUser(ownerId);
        return storage
                .getAllBookingsByOwner(ownerId, state)
                .stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookingDto find(Long bookingId, Long userId) {
        Booking booking = bookingValidator.checkByUserOrOwner(userId, bookingId);
        return BookingMapper.toDto(booking);
    }

    public BookingDto create(Long userId, RequestBookingDto request) {
        Booking booking = bookingValidator.checkByUser(userId, request);
        booking = storage.create(booking);
        log.info("Бронирование запрошено (ID={})", booking.getId());
        return BookingMapper.toDto(booking);
    }

    public BookingDto changeStatus(Long ownerId, Long bookingId, boolean approved) {
        Booking booking = bookingValidator.checkByOwner(ownerId, bookingId);
        if (approved) {
            booking = storage.approved(booking);
            log.info("Бронь подтверждена владельцем (ID={})", bookingId);
            return BookingMapper.toDto(booking);
        }
        booking = storage.rejected(booking);
        log.info("Бронь Отклонена владельцем (ID={})", bookingId);
        return BookingMapper.toDto(booking);
    }
}
