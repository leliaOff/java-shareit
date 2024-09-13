package ru.practicum.shareit.request.storage;

import ru.practicum.shareit.request.model.Request;

import java.util.Collection;
import java.util.Optional;

public interface RequestStorage {
    Collection<Request> getByUser(Long userId);

    Collection<Request> getAll();

    Optional<Request> find(Long id);

    Request create(Request request);
}
