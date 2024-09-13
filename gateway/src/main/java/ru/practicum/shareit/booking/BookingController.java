package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.request.BookingRequest;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient client;

    @Autowired
    BookingController(BookingClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestParam(required = false) BookingFilterState state) {
        return client.getAllBookings(userId, state != null ? state : BookingFilterState.ALL);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                        @RequestParam(required = false) BookingFilterState state) {
        return client.getAllBookingsByOwner(ownerId, state != null ? state : BookingFilterState.ALL);
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> find(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable Long bookingId) {
        return client.find(bookingId, userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody BookingRequest request) {
        return client.create(userId, request);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> changeStatus(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                               @PathVariable long bookingId,
                                               @RequestParam boolean approved) {
        return client.changeStatus(ownerId, bookingId, approved);
    }
}
