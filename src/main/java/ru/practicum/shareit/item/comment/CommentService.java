package ru.practicum.shareit.item.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.RequestCommentDto;
import ru.practicum.shareit.item.comment.storage.CommentStorage;

@Service
@Slf4j
public class CommentService {
    @Qualifier("dbCommentStorage")
    private final CommentStorage storage;

    private final CommentValidator commentValidator;

    @Autowired
    CommentService(@Qualifier("dbCommentStorage") CommentStorage storage, CommentValidator commentValidator) {
        this.storage = storage;
        this.commentValidator = commentValidator;
    }

    public CommentDto create(Long itemId, Long userId, RequestCommentDto request) {
        Comment comment = commentValidator.check(itemId, userId, request);
        comment = storage.create(comment);
        log.info("Оставлен новый комментарий (ID={})", comment.getId());
        return CommentMapper.toDto(comment);
    }
}
