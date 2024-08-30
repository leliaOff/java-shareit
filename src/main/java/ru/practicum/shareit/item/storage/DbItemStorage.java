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

    public Collection<Item> getAllItems(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId);
    }

    public Collection<Item> search(String text) {
        return itemRepository.findByNameContainingOrDescriptionContaining(text, text);
    }

    public Optional<Item> find(Long id) {
        return itemRepository.findById(id);
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Long id, Item item) {
        item.setId(id);
        return itemRepository.save(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
