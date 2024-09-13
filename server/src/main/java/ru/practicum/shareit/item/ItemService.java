package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService {
    @Qualifier("dbItemStorage")
    private final ItemStorage storage;

    private final ItemValidator itemValidator;

    @Autowired
    ItemService(@Qualifier("dbItemStorage") ItemStorage storage, ItemValidator itemValidator) {
        this.storage = storage;
        this.itemValidator = itemValidator;
    }

    public Collection<ItemDto> getAllItems(Long ownerId) {
        return storage
                .getAllItems(ownerId)
                .stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return storage
                .search(text)
                .stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto find(Long id) {
        return storage
                .find(id)
                .map(ItemMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
    }

    public ItemDto create(Long ownerId, RequestItemDto request) {
        itemValidator.check(ownerId, request);
        Item item = ItemMapper.toModel(request);
        item.setOwnerId(ownerId);
        item = storage.create(item);
        log.info("Вещь добавлена (ID={})", item.getId());
        return ItemMapper.toDto(item);
    }

    public ItemDto update(Long ownerId, Long id, RequestItemDto request) {
        Item currentItem = itemValidator.check(id, ownerId);
        Item item = storage.update(id, ItemMapper.mergeToModel(currentItem, request));
        log.info("Вещь изменена (ID={})", id);
        return ItemMapper.toDto(item);
    }

    public void delete(Long ownerId, Long id) {
        Item currentItem = itemValidator.check(id, ownerId);
        storage.delete(currentItem);
    }
}
