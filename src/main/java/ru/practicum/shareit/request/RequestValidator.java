package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Optional;

@Service
@Slf4j
public class RequestValidator {
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;

    @Autowired
    RequestValidator(@Qualifier("dbUserStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Request check(Long userId, RequestRequestDto dto) {
        checkDescription(dto);
        checkUser(userId);
        return RequestMapper.toModel(dto);
    }

    private void checkDescription(RequestRequestDto dto) {
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            log.error("Необходимо указать описание запроса");
            throw new ValidationException("Необходимо указать описание запроса");
        }
    }

    private void checkUser(Long userId) {
        Optional<User> optional = userStorage.find(userId);
        if (optional.isEmpty()) {
            log.error("Пользователь не найден (ID={})", userId);
            throw new NotFoundException("Пользователь не найден");
        }
    }
}
