package ru.practicum.shareit.booking.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.enums.BookingFilterState;
import ru.practicum.shareit.booking.enums.BookingStatus;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbItemStorage")
public class DbBookingStorage implements BookingStorage {
    private final BookingRepository bookingRepository;

    public DbBookingStorage(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Collection<Booking> getAllBookings(Long userId, BookingFilterState state) {
        if (state.equals(BookingFilterState.CURRENT)) { // текущие
            return bookingRepository.findByUserIdAndStartLessThanEqualAndEndGreaterThan(userId, Instant.now(), Instant.now());
        }
        if (state.equals(BookingFilterState.PAST)) { // завершённые
            return bookingRepository.findByUserIdAndEndLessThanEqual(userId, Instant.now());
        }
        if (state.equals(BookingFilterState.FUTURE)) { // будущие
            return bookingRepository.findByUserIdAndStartGreaterThan(userId, Instant.now());
        }
        if (state.equals(BookingFilterState.WAITING)) {
            return bookingRepository.findByUserIdAndStatus(userId, BookingStatus.WAITING);
        }
        if (state.equals(BookingFilterState.REJECTED)) {
            return bookingRepository.findByUserIdAndStatus(userId, BookingStatus.REJECTED);
        }
        return bookingRepository.findByUserId(userId);
    }

    public Collection<Booking> getAllBookingsByOwner(Long ownerId, BookingFilterState state) {
        if (state.equals(BookingFilterState.CURRENT)) { // текущие
            return bookingRepository.findByUserIdAndStartLessThanEqualAndEndGreaterThan(ownerId, Instant.now(), Instant.now());
        }
        if (state.equals(BookingFilterState.PAST)) { // завершённые
            return bookingRepository.findByUserIdAndEndLessThanEqual(ownerId, Instant.now());
        }
        if (state.equals(BookingFilterState.FUTURE)) { // будущие
            return bookingRepository.findByUserIdAndStartGreaterThan(ownerId, Instant.now());
        }
        if (state.equals(BookingFilterState.WAITING)) {
            return bookingRepository.findByUserIdAndStatus(ownerId, BookingStatus.WAITING);
        }
        if (state.equals(BookingFilterState.REJECTED)) {
            return bookingRepository.findByUserIdAndStatus(ownerId, BookingStatus.REJECTED);
        }
        return bookingRepository.findByUserId(ownerId);
    }

    public Optional<Booking> find(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking approved(Booking booking) {
        booking.setStatus(BookingStatus.APPROVED);
        return bookingRepository.save(booking);
    }

    public Booking rejected(Booking booking) {
        booking.setStatus(BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

    public Collection<Booking> findApprovedUserItemBooking(Long itemId, Long userId) {
        return bookingRepository.findByItemIdAndUserIdAndStatus(itemId, userId, BookingStatus.APPROVED);
    }
}
