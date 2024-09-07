package ru.practicum.shareit.request;

import ru.practicum.shareit.base.helpers.DateTimeHelper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.model.Request;

import java.util.stream.Collectors;

public class RequestMapper {
    public static RequestDto toDto(Request model) {
        return new RequestDto(
                model.getId(),
                model.getUserId(),
                model.getDescription(),
                DateTimeHelper.toString(model.getCreated()),
                model.getItems().stream()
                        .map(RequestMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public static RequestItemDto toDto(Item model) {
        return new RequestItemDto(
                model.getId(),
                model.getOwnerId(),
                model.getName()
        );
    }

    public static Request toModel(RequestRequestDto dto) {
        return new Request(dto.getDescription());
    }
}
