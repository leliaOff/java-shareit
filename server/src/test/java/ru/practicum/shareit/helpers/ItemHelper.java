package ru.practicum.shareit.helpers;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.RequestItemDto;

public class ItemHelper {

    public static RequestItemDto getRequestItemDto(String name, String description, boolean available) {
        RequestItemDto requestItemDto = new RequestItemDto();
        requestItemDto.setName(name);
        requestItemDto.setDescription(description);
        requestItemDto.setAvailable(available);
        return requestItemDto;
    }

    public static ItemDto getItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setOwnerId(1L);
        itemDto.setName("Скарлетт О’Хара");
        itemDto.setDescription("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
        itemDto.setAvailable(true);
        return itemDto;
    }
}
