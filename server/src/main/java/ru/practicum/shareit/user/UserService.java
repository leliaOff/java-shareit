package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.InternalServerException;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Qualifier("dbUserStorage")
    private final UserStorage storage;

    @Autowired
    UserService(@Qualifier("dbUserStorage") UserStorage storage) {
        this.storage = storage;
    }

    public Collection<UserDto> getAllUsers() {
        return storage
                .getAllUsers()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto find(Long id) {
        return storage
                .find(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public UserDto create(UserDto request) {
        if (storage.find(request.getEmail()).isPresent()) {
            log.error("Не удалось создать пользователя: такой email существует");
            throw new InternalServerException("Не удалось создать пользователя: такой email уже существует");
        }
        User user = UserMapper.toModel(request);
        user = storage.create(user);
        log.info("Пользователь добавлен (ID={})", user.getId());
        return UserMapper.toDto(user);
    }

    public UserDto update(Long id, UserDto request) {
        Optional<User> duplicateUser = storage.find(request.getEmail());
        if (duplicateUser.isPresent() && !Objects.equals(duplicateUser.get().getId(), id)) {
            log.error("Не удалось изменить пользователя: такой email уже существует");
            throw new InternalServerException("Не удалось изменить пользователя: такой email уже существует");
        }
        Optional<User> currentUser = storage.find(id);
        if (currentUser.isEmpty()) {
            log.error("Пользователь не найден (ID={})", id);
            throw new NotFoundException("Пользователь не найден");
        }
        User user = storage.update(id, UserMapper.mergeToModel(currentUser.get(), request));
        log.info("Пользователь изменен (ID={})", id);
        return UserMapper.toDto(user);
    }

    public void delete(Long id) {
        Optional<User> currentUser = storage.find(id);
        if (currentUser.isEmpty()) {
            log.error("Пользователь не найден (ID={})", id);
            throw new NotFoundException("Пользователь не найден");
        }
        storage.delete(currentUser.get());
    }
}
