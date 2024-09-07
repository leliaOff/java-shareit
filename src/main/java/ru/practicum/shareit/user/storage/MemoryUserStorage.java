package ru.practicum.shareit.user.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.base.Helper;
import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
@Qualifier("memoryUserStorage")
public class MemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Optional<User> find(Long id) {
        return users.containsKey(id) ? Optional.of(users.get(id)) : Optional.empty();
    }

    public Optional<User> find(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public User create(User user) {
        user.setId(Helper.nextId(users));
        users.put(user.getId(), user);
        return user;
    }

    public User update(Long id, User user) {
        users.put(id, user);
        return user;
    }

    public void delete(User user) {
        users.remove(user.getId());
    }
}
