package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> getAllUsers();

    Optional<User> find(Long id);

    Optional<User> find(String email);

    User create(User user);

    User update(Long id, User user);

    void delete(User user);
}
