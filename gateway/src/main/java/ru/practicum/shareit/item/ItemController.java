package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.request.CommentRequest;
import ru.practicum.shareit.item.request.ItemRequest;

@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemClient client;

    @Autowired
    ItemController(ItemClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return client.getAllItems(ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text) {
        return client.search(text);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Long id) {
        return client.find(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                         @RequestBody ItemRequest request) {
        return client.create(ownerId, request.validate());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                         @PathVariable long id,
                                         @RequestBody ItemRequest request) {
        return client.update(ownerId, id, request.validate());
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                       @PathVariable long id) {
        client.delete(ownerId, id);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @PathVariable Long id,
                                                @RequestBody CommentRequest request) {
        return client.createComment(id, userId, request.validate());
    }
}
