package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.request.RequestRequest;

@RestController
@RequestMapping(path = "/requests")
public class RequestController {
    private final RequestClient client;

    @Autowired
    RequestController(RequestClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<Object> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return client.getByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return client.getAll();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> find(@PathVariable Long requestId) {
        return client.find(requestId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody RequestRequest request) {
        return client.create(userId, request.validate());
    }
}
