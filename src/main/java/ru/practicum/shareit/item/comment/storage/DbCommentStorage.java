package ru.practicum.shareit.item.comment.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;

@Component
@Qualifier("dbCommentStorage")
public class DbCommentStorage implements CommentStorage {
    private final CommentRepository commentRepository;

    public DbCommentStorage(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }
}
