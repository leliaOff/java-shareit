package ru.practicum.shareit.item;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private boolean available;
}
