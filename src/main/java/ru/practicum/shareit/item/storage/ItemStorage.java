package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {
    Collection<Item> getAllItems(Long ownerId);

    Collection<Item> search(String text);

    Optional<Item> find(Long id);

    Item create(Item item);

    Item update(Long id, Item item);

    void delete(Item item);
}
