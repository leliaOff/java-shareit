package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.base.helpers.DateTimeHelper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.RequestCommentDto;
import ru.practicum.shareit.item.model.Comment;

public class CommentMapper {
    public static Comment requestToModel(RequestCommentDto dto) {
        return new Comment(dto.getText());
    }

    public static CommentDto toDto(Comment model) {
        return new CommentDto(model.getId(), model.getText(), model.getUser().getName(), DateTimeHelper.toString(model.getCreated()));
    }
}
