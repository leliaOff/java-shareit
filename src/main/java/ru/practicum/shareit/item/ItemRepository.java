package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Map;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> findByOwnerId(Long ownerId);

    Collection<Item> findByNameContainingIgnoreCaseAndAvailable(String text, Boolean available);

    @Query(value = "SELECT items.id, " +
            "items.owner_id, " +
            "items.name, " +
            "items.description, " +
            "items.available, " +
            "last_booking.last_booking_date as last, " +
            "nearest_booking.nearest_booking_date as nearest " +
            "FROM items " +
            "LEFT JOIN ( " +
            "SELECT item_id, " +
            "max(date_start) as last_booking_date " +
            "FROM bookings " +
            "GROUP BY item_id) as last_booking ON last_booking.item_id = items.id " +
            "LEFT JOIN ( " +
            "SELECT item_id, " +
            "min(date_start) as nearest_booking_date " +
            "FROM bookings " +
            "WHERE date_start >= now() " +
            "GROUP BY item_id) as nearest_booking ON nearest_booking.item_id = items.id " +
            "WHERE owner_id = ?1", nativeQuery = true)
    Collection<Map<String, Object>> getItemsBookingDate(Long ownerId);
}
