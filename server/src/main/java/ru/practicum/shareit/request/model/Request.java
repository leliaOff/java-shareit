package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant created;

    @OneToMany
    @JoinColumn(name = "request_id")
    private Set<Item> items = new HashSet<Item>();

    public Request() {
        this.created = Instant.now();
    }

    public Request(String description) {
        this.description = description;
        this.created = Instant.now();
    }
}
