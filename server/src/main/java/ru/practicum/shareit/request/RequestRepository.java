package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.Request;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Collection<Request> findByUserIdOrderByCreatedDesc(Long userId);

    Collection<Request> findAllByOrderByCreatedDesc();
}
