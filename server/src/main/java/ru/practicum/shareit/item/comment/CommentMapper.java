package ru.practicum.shareit.item.comment;

import ru.practicum.shareit.base.DateTimeHelper;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.comment.dto.RequestCommentDto;

public class CommentMapper {
    public static Comment requestToModel(RequestCommentDto dto) {
        return new Comment(dto.getText());
    }

    public static CommentDto toDto(Comment model) {
        return new CommentDto(model.getId(), model.getText(), model.getUser().getName(), DateTimeHelper.toString(model.getCreated()));
    }
}
