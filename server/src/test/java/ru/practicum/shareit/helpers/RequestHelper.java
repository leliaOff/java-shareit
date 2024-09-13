package ru.practicum.shareit.helpers;

import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;

import java.util.ArrayList;

public class RequestHelper {

    public static RequestDto getRequestDto() {
        return new RequestDto(
                1L,
                1L,
                "Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца",
                "2024-09-01T10:00:00",
                new ArrayList<>()
        );
    }

    public static RequestDto getRequestDto(Long id, Long userId, String description, String created) {
        return new RequestDto(
                id,
                userId,
                description,
                created,
                new ArrayList<>()
        );
    }

    public static RequestRequestDto getRequestRequestDto() {
        return new RequestRequestDto("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
    }
}
