package ru.practicum.shareit.item.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.base.Helper;
import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
@Qualifier("memoryItemStorage")
public class MemoryItemStorage implements ItemStorage {
    private final HashMap<Long, Item> items = new HashMap<>();

    public Collection<Item> getAllItems(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .toList();
    }

    public Collection<Item> search(String text) {
        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase())) &&
                        item.isAvailable())
                .toList();
    }

    public Optional<Item> find(Long id) {
        return items.containsKey(id) ? Optional.of(items.get(id)) : Optional.empty();
    }

    public Item create(Item item) {
        item.setId(Helper.nextId(items));
        items.put(item.getId(), item);
        return item;
    }

    public Item update(Long id, Item item) {
        items.put(id, item);
        return item;
    }

    public void delete(Item item) {
        items.remove(item.getId());
    }
}
