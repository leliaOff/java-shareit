package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import ru.practicum.shareit.booking.enums.BookingState;

import java.time.Instant;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "date_from", nullable = false)
    private Instant dateFrom;

    @Column(name = "date_to", nullable = false)
    private Instant dateTo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingState state;

    private String review;
}
