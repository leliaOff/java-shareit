package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.base.helpers.DateTimeHelper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Data
public class ItemBookingDate {
    private Long id;

    private Long ownerId;

    private String name;

    private String description;

    private boolean available;

    private Instant last;

    private Instant nearest;

    public ItemBookingDate(Map<String, Object> item) {
        this.id = (Long) item.get("id");
        this.ownerId = (Long) item.get("owner_id");
        this.name = (String) item.get("name");
        this.description = (String) item.get("description");
        this.available = (Boolean) item.get("available");
        this.last = DateTimeHelper.toInstant((Timestamp) item.get("last"));
        this.nearest = DateTimeHelper.toInstant((Timestamp) item.get("nearest"));
    }
}
