package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.model.Request;

@Service
@Slf4j
public class RequestValidator {
    public Request check(RequestRequestDto dto) {
        checkDescription(dto);
        return RequestMapper.toModel(dto);
    }

    private void checkDescription(RequestRequestDto dto) {
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            log.error("Необходимо указать описание запроса");
            throw new ValidationException("Необходимо указать описание запроса");
        }
    }
}
