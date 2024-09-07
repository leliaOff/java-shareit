package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.request.UserRequest;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserClient client;

    @Autowired
    UserController(UserClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return client.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Long id) {
        return client.find(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserRequest request) {
        return client.create(request.validate());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody UserRequest request) {
        return client.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        client.delete(id);
    }
}
