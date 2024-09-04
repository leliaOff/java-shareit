package ru.practicum.shareit.item.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemBookingDate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier("dbItemStorage")
public class DbItemStorage implements ItemStorage {
    private final ItemRepository itemRepository;

    public DbItemStorage(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Collection<ItemBookingDate> getAllItems(Long ownerId) {
        Collection<Map<String, Object>> result = itemRepository.getItemsBookingDate(ownerId);
        return result.stream()
                .map(ItemBookingDate::new)
                .collect(Collectors.toList());
    }

    public Collection<Item> search(String text) {
        return itemRepository.findByNameContainingIgnoreCaseAndAvailable(text, true);
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
