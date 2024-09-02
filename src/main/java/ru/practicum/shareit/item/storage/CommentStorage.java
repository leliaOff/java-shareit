package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Comment;

public interface CommentStorage {
    Comment create(Comment comment);
}
