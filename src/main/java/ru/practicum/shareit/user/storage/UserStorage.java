package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> get();

    Optional<User> find(Long id);

    Optional<User> find(String email);

    Optional<User> create(User user);

    Optional<User> update(Long id, User user);

    void delete(User user);
}
