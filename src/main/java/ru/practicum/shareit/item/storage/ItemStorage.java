package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemBookingDate;

import java.util.Collection;
import java.util.Optional;

public interface ItemStorage {
    Collection<ItemBookingDate> getAllItems(Long ownerId);

    Collection<Item> search(String text);

    Optional<Item> find(Long id);

    Item create(Item item);

    Item update(Long id, Item item);

    void delete(Item item);
}
