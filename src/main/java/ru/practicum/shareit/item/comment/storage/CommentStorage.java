package ru.practicum.shareit.item.comment.storage;

import ru.practicum.shareit.item.comment.Comment;

public interface CommentStorage {
    Comment create(Comment comment);
}
