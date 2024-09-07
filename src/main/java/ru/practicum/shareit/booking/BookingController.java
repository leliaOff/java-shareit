package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.enums.BookingFilterState;

import java.util.Collection;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public Collection<BookingDto> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(required = false) BookingFilterState state) {
        return bookingService.getAllBookings(userId, state != null ? state : BookingFilterState.ALL);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                        @RequestParam(required = false) BookingFilterState state) {
        return bookingService.getAllBookingsByOwner(ownerId, state != null ? state : BookingFilterState.ALL);
    }


    @GetMapping("/{bookingId}")
    public BookingDto find(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable long bookingId) {
        return bookingService.find(bookingId, userId);
    }

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @RequestBody RequestBookingDto request) {
        return bookingService.create(userId, request);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto changeStatus(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                   @PathVariable long bookingId,
                                   @RequestParam boolean approved) {
        return bookingService.changeStatus(ownerId, bookingId, approved);
    }
}
