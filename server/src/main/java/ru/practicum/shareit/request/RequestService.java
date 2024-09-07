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
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {
    @Qualifier("dbRequestStorage")
    private final RequestStorage storage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;

    @Autowired
    RequestService(@Qualifier("dbRequestStorage") RequestStorage storage,
                   @Qualifier("dbUserStorage") UserStorage userStorage) {
        this.storage = storage;
        this.userStorage = userStorage;
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
        Optional<User> optional = userStorage.find(userId);
        if (optional.isEmpty()) {
            log.error("Пользователь не найден (ID={})", userId);
            throw new NotFoundException("Пользователь не найден");
        }
        Request request = RequestMapper.toModel(dto);
        request.setUserId(userId);
        request = storage.create(request);
        log.info("Создан новый запрос (ID={})", request.getId());
        return RequestMapper.toDto(request);
    }
}
