package ru.practicum.shareit.item.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ItemDto {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean available;
    private String lastBooking;
    private String nextBooking;
    private Collection<String> comments;
}
