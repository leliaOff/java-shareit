package ru.practicum.shareit.user.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("dbUserStorage")
public class DbUserStorage implements UserStorage {
    private final UserRepository userRepository;

    public DbUserStorage(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> find(Long id) {
        Optional<User> user =  userRepository.findById(id);
        return userRepository.findById(id);
    }

    public Optional<User> find(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
