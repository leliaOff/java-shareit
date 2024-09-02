package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Instant created;

    public Comment(String text) {
        this.text = text;
        this.created = Instant.now();
    }
}
