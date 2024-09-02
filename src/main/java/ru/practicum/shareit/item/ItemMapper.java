package ru.practicum.shareit.item;

import ru.practicum.shareit.base.helpers.DateTimeHelper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemBookingDate;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwnerId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        return dto;
    }
    public static ItemDto toDto(ItemBookingDate item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwnerId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        dto.setLastBooking(DateTimeHelper.toString(item.getLast()));
        dto.setNextBooking(DateTimeHelper.toString(item.getNearest()));
        return dto;
    }

    public static Item toModel(ItemDto item) {
        Item model = new Item();
        model.setId(item.getId());
        model.setOwnerId(item.getOwnerId());
        model.setName(item.getName());
        model.setDescription(item.getDescription());
        model.setAvailable(Boolean.TRUE.equals(item.getAvailable()));
        return model;
    }

    public static Item mergeToModel(Item model, ItemDto item) {
        if (item.getName() != null) {
            model.setName(item.getName());
        }
        if (item.getDescription() != null) {
            model.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            model.setAvailable(Boolean.TRUE.equals(item.getAvailable()));
        }
        return model;
    }
}
