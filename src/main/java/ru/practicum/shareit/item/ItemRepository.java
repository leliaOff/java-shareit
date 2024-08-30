package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> findByOwnerId(Long ownerId);

    Collection<Item> findByNameContainingOrDescriptionContaining(String text, String text1);
}
