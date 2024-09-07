package ru.practicum.shareit.request.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.model.Request;

import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbRequestStorage")
public class DbRequestStorage implements RequestStorage {
    private final RequestRepository repository;

    public DbRequestStorage(RequestRepository repository) {
        this.repository = repository;
    }

    public Collection<Request> getByUser(Long userId) {
        return repository.findByUserIdOrderByCreatedDesc(userId);
    }

    public Collection<Request> getAll() {
        return repository.findAllByOrderByCreatedDesc();
    }

    public Optional<Request> find(Long id) {
        return repository.findById(id);
    }

    public Request create(Request request) {
        return repository.save(request);
    }
}
