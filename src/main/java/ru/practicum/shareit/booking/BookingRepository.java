package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.enums.BookingStatus;

import java.time.Instant;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findByUserId(Long userId);

    Collection<Booking> findByUserIdAndStatus(Long userId, BookingStatus bookingState);

    Collection<Booking> findByItemIdAndUserIdAndStatusAndEndLessThan(Long itemId, Long userId, BookingStatus bookingState, Instant now);

    Collection<Booking> findByUserIdAndStartLessThanEqualAndEndGreaterThan(Long userId, Instant now, Instant now1);

    Collection<Booking> findByUserIdAndEndLessThan(Long userId, Instant now);

    Collection<Booking> findByUserIdAndStartGreaterThan(Long userId, Instant now);
}
