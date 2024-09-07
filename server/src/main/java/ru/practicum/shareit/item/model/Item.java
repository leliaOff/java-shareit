package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    private String description;

    private boolean available;

    @ElementCollection
    @CollectionTable(name = "comments", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "text")
    private Collection<String> comments = new ArrayList<>();

    @Column(name = "request_id")
    private Long requestId;
}
