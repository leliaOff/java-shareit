package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {
    Collection<Item> get(Long ownerId);

    Collection<Item> search(String text);

    Optional<Item> find(Long id);

    Optional<Item> create(Item item);

    Optional<Item> update(Long id, Item item);

    void delete(Item item);
}
