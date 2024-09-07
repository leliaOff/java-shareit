package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.storage.RequestStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Optional;

@Service
@Slf4j
public class ItemValidator {
    @Qualifier("dbItemStorage")
    private final ItemStorage itemStorage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;
    @Qualifier("dbRequestStorage")
    private final RequestStorage requestStorage;

    @Autowired
    ItemValidator(@Qualifier("dbItemStorage") ItemStorage itemStorage,
                  @Qualifier("dbUserStorage") UserStorage userStorage,
                  @Qualifier("dbRequestStorage") RequestStorage requestStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
        this.requestStorage = requestStorage;
    }

    public void check(Long ownerId, RequestItemDto request) {
        checkOwnerExists(ownerId);
        checkName(request);
        checkDescription(request);
        checkAvailable(request);
        checkRequestId(request);
    }

    public Item check(Long itemId, Long ownerId) {
        Item item = checkExists(itemId);
        checkOwner(item, ownerId);
        return item;
    }

    private Item checkExists(Long itemId) {
        Optional<Item> item = itemStorage.find(itemId);
        if (item.isEmpty()) {
            log.error("Вещь не найдена (ID={})", itemId);
            throw new NotFoundException("Вещь не найдена");
        }
        return item.get();
    }

    private void checkOwner(Item item, Long ownerId) {
        if (!item.getOwnerId().equals(ownerId)) {
            log.error("Попытка внести изменения не в свою вещь (UserID={})", ownerId);
            throw new NotFoundException("Данные вещь вам не принадлежит");
        }
    }

    private void checkOwnerExists(Long ownerId) {
        Optional<User> user = userStorage.find(ownerId);
        if (user.isEmpty()) {
            log.error("Пользователь не найден (ID={})", ownerId);
            throw new NotFoundException("Пользователь не найден (ID={})");
        }
    }

    private void checkName(RequestItemDto request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            log.error("Необходимо указать наименование вещи");
            throw new ValidationException("Необходимо указать наименование вещи");
        }
    }

    private void checkDescription(RequestItemDto request) {
        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("Необходимо указать описание вещи");
            throw new ValidationException("Необходимо указать описание вещи");
        }
    }

    private void checkAvailable(RequestItemDto request) {
        if (request.getAvailable() == null) {
            log.error("Необходимо указать доступность вещи");
            throw new ValidationException("Необходимо указать доступность вещи");
        }
    }

    private void checkRequestId(RequestItemDto request) {
        if (request.getRequestId() == null) {
            return;
        }
        Optional<Request> optional = requestStorage.find(request.getRequestId());
        if (optional.isEmpty()) {
            log.error("Запрос не найден (ID={})", request.getRequestId());
            throw new NotFoundException("Запрос не найден (ID={})");
        }
    }
}
