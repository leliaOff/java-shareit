package ru.practicum.shareit.user.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbUserStorage")
public class DbUserStorage implements UserStorage {
    private final UserRepository userRepository;

    public DbUserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> get() {
        return userRepository.get();
    }

    public Optional<User> find(Long id) {
        return userRepository.find(id);
    }

    public Optional<User> find(String email) {
        return userRepository.find(email);
    }

    public Optional<User> create(User user) {
        return userRepository.create(user);
    }

    public Optional<User> update(Long id, User user) {
        user.setId(id);
        return userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
