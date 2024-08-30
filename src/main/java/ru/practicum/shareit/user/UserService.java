package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.InternalServerException;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
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

    private final UserValidator validator;

    @Autowired
    UserService(@Qualifier("dbUserStorage") UserStorage storage, UserValidator validator) {
        this.storage = storage;
        this.validator = validator;
    }

    public Collection<UserDto> get() {
        return storage
                .get()
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
        if (!validator.checkEmail(request.getEmail())) {
            log.error("Не удалось создать пользователя: email [" + request.getEmail() + "] недействительный");
            throw new ValidationException("Не удалось создать пользователя: email [" + request.getEmail() + "] недействительный");
        }
        User user = UserMapper.toModel(request);
        user = storage.create(user).orElseThrow(() -> {
            log.error("Не удалось создать пользователя");
            return new InternalServerException("Не удалось создать пользователя");
        });
        log.info("Пользователь добавлен (ID={})", user.getId());
        return UserMapper.toDto(user);
    }

    public UserDto update(Long id, UserDto request) {
        Optional<User> duplicateUser = storage.find(request.getEmail());
        if (duplicateUser.isPresent() && !Objects.equals(duplicateUser.get().getId(), id)) {
            log.error("Не удалось изменить пользователя: такой email уже существует");
            throw new InternalServerException("Не удалось изменить пользователя: такой email уже существует");
        }
        if (request.getEmail() != null && !validator.checkEmail(request.getEmail())) {
            log.error("Не удалось создать пользователя: email [" + request.getEmail() + "] недействительный");
            throw new ValidationException("Не удалось создать пользователя: email [" + request.getEmail() + "] недействительный");
        }
        Optional<User> currentUser = storage.find(id);
        if (currentUser.isEmpty()) {
            log.error("Пользователь не найден (ID={})", id);
            throw new NotFoundException("Пользователь не найден");
        }
        User user = storage.update(id, UserMapper.mergeToModel(currentUser.get(), request)).orElseThrow(() -> {
            log.error("Пользователь не найден (ID={})", id);
            return new NotFoundException("Пользователь не найден");
        });
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
