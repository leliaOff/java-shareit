package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.enums.BookingFilterState;

import java.util.Collection;
import java.util.Optional;

public interface BookingStorage {
    Collection<Booking> getAllBookings(Long userId, BookingFilterState state);

    Collection<Booking> getAllBookingsByOwner(Long ownerId, BookingFilterState state);

    Optional<Booking> find(Long id);

    Booking create(Booking booking);

    Booking approved(Booking booking);

    Booking rejected(Booking booking);

    Collection<Booking> findApprovedUserItemBooking(Long itemId, Long userId);
}
