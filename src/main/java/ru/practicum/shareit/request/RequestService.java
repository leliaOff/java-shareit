package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.storage.RequestStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {
    @Qualifier("dbRequestStorage")
    private final RequestStorage storage;

    private final RequestValidator validator;

    @Autowired
    RequestService(@Qualifier("dbRequestStorage") RequestStorage storage,
                   RequestValidator validator) {
        this.storage = storage;
        this.validator = validator;
    }

    public Collection<RequestDto> getByUser(Long userId) {
        return storage.getByUser(userId)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<RequestDto> getAll() {
        return storage.getAll()
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto find(Long requestId) {
        Optional<Request> optional = storage.find(requestId);
        if (optional.isEmpty()) {
            log.error("Запрос не найден (ID={})", requestId);
            throw new NotFoundException("Запрос не найден");
        }
        return RequestMapper.toDto(optional.get());
    }

    public RequestDto create(Long userId, RequestRequestDto dto) {
        Request request = validator.check(userId, dto);
        request.setUserId(userId);
        request = storage.create(request);
        log.info("Создан новый запрос (ID={})", request.getId());
        return RequestMapper.toDto(request);
    }
}
