package ru.practicum.shareit.item;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setOwnerId(resultSet.getLong("owner_id"));
        item.setName(resultSet.getString("name"));
        item.setDescription(resultSet.getString("description"));
        item.setAvailable(resultSet.getBoolean("available"));
        return item;
    }
}
