package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
public class RequestController {
    private final RequestService service;

    @Autowired
    RequestController(RequestService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<RequestDto> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/all")
    public Collection<RequestDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{requestId}")
    public RequestDto find(@PathVariable long requestId) {
        return service.find(requestId);
    }

    @PostMapping
    public RequestDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @RequestBody RequestRequestDto request) {
        return service.create(userId, request);
    }
}
