package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private Long userId;
    private String description;
    private String created;
    private Collection<RequestItemDto> items;
}
