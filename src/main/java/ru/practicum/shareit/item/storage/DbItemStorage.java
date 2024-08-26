package ru.practicum.shareit.item.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;

import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbItemStorage")
public class DbItemStorage implements ItemStorage {
    private final ItemRepository itemRepository;

    public DbItemStorage(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Collection<Item> get(Long ownerId) {
        return itemRepository.get(ownerId);
    }

    public Collection<Item> search(String text) {
        return itemRepository.search(text);
    }

    public Optional<Item> find(Long id) {
        return itemRepository.find(id);
    }

    public Optional<Item> create(Item item) {
        return itemRepository.create(item);
    }

    public Optional<Item> update(Long id, Item item) {
        item.setId(id);
        return itemRepository.update(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
