package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.base.BaseRepository;
import ru.practicum.shareit.base.exception.InternalServerException;

import java.util.Collection;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository extends BaseRepository<User> {

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Collection<User> get() {
        return get("SELECT * FROM users");
    }

    public Optional<User> find(Long id) {
        return find("SELECT * FROM users WHERE id = ?", id);
    }

    public Optional<User> find(String email) {
        return find("SELECT * FROM users WHERE email = ?", email);
    }

    public Optional<User> create(User user) {
        try {
            Long id = create(
                    "INSERT INTO users(email, name) VALUES (?, ?)",
                    user.getEmail(),
                    user.getName()
            );
            user.setId(id);
        } catch (InternalServerException exception) {
            log.error("При добавлении пользователя произошла ошибка: " + exception.getMessage());
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> update(User user) {
        try {
            update(
                    "UPDATE users SET email = ?, name = ? WHERE id = ?",
                    user.getEmail(),
                    user.getName(),
                    user.getId()
            );
        } catch (InternalServerException exception) {
            log.error("При изменении пользователя произошла ошибка: " + exception.getMessage());
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public void delete(User user) {
        delete(
                "DELETE FROM users WHERE id = ?",
                user.getId()
        );
    }
}
