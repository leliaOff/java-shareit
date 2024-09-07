package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.request.BookingRequest;
import ru.practicum.shareit.client.BaseClient;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllBookings(Long userId, BookingFilterState state) {
        return get("?state=" + state, userId);
    }

    public ResponseEntity<Object> getAllBookingsByOwner(Long ownerId, BookingFilterState state) {
        return get("/owner?state=" + state, ownerId);
    }

    public ResponseEntity<Object> find(Long id, Long userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> create(Long userId, BookingRequest request) {
        return post("", userId, request);
    }

    public ResponseEntity<Object> changeStatus(Long ownerId, Long id, boolean approved) {
        return patch("/" + id + "?approved=" + (approved ? "true" : "false"), ownerId, null);
    }
}
