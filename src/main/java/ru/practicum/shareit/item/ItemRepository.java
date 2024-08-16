package ru.practicum.shareit.item;

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
public class ItemRepository extends BaseRepository<Item> {

    public ItemRepository(JdbcTemplate jdbc, RowMapper<Item> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Item> get(Long ownerId) {
        return get("SELECT * FROM items WHERE owner_id = ?", ownerId);
    }

    public Collection<Item> search(String text) {
        return get("SELECT * FROM items WHERE (name ILIKE ? OR description ILIKE ?) AND available = true", '%' + text + '%', '%' + text + '%');
    }

    public Optional<Item> find(Long id) {
        return find("SELECT * FROM items WHERE id = ?", id);
    }

    public Optional<Item> create(Item item) {
        try {
            Long id = create(
                    "INSERT INTO items(owner_id, name, description, available) VALUES (?, ?, ?, ?)",
                    item.getOwnerId(),
                    item.getName(),
                    item.getDescription(),
                    item.isAvailable()
            );
            item.setId(id);
        } catch (InternalServerException exception) {
            log.error("При добавлении вещи произошла ошибка: " + exception.getMessage());
            return Optional.empty();
        }
        return Optional.of(item);
    }

    public Optional<Item> update(Item item) {
        try {
            update(
                    "UPDATE items SET name = ?, description = ?, available = ? WHERE id = ?",
                    item.getName(),
                    item.getDescription(),
                    item.isAvailable(),
                    item.getId()
            );
        } catch (InternalServerException exception) {
            log.error("При изменении вещи произошла ошибка: " + exception.getMessage());
            return Optional.empty();
        }
        return Optional.of(item);
    }

    public void delete(Item item) {
        delete(
                "DELETE FROM items WHERE id = ?",
                item.getId()
        );
    }
}
