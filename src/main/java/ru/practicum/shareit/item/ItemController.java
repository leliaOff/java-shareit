package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Collection<ItemDto> get(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.get(ownerId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @GetMapping("/{id}")
    public ItemDto find(@PathVariable long id) {
        return itemService.find(id);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                          @RequestBody ItemDto request) {
        return itemService.create(ownerId, request);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                          @PathVariable long id,
                          @RequestBody ItemDto request) {
        return itemService.update(ownerId, id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                       @PathVariable long id) {
        itemService.delete(ownerId, id);
    }
}
