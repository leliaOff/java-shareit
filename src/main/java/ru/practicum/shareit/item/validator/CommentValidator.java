package ru.practicum.shareit.item.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.dto.RequestCommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@Qualifier("commentValidator")
public class CommentValidator {
    @Qualifier("dbItemStorage")
    private final ItemStorage itemStorage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;
    @Qualifier("dbBookingStorage")
    private final BookingStorage bookingStorage;

    @Autowired
    CommentValidator(@Qualifier("dbItemStorage") ItemStorage itemStorage,
                     @Qualifier("dbUserStorage") UserStorage userStorage,
                     @Qualifier("dbBookingStorage") BookingStorage bookingStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
        this.bookingStorage = bookingStorage;
    }

    public Comment check(Long itemId, Long userId, RequestCommentDto request) {
        Item item = checkExists(itemId);
        User user = checkCommentator(item, userId);
        checkComment(request);
        Comment comment = CommentMapper.requestToModel(request);
        comment.setUser(user);
        comment.setItem(item);
        return comment;
    }

    private Item checkExists(Long itemId) {
        Optional<Item> item = itemStorage.find(itemId);
        if (item.isEmpty()) {
            log.error("Вещь не найдена (ID={})", itemId);
            throw new NotFoundException("Вещь не найдена");
        }
        return item.get();
    }

    private User checkCommentator(Item item, Long userId) {
        User user = checkUserExists(userId);
        Collection<Booking> bookings = bookingStorage.findApprovedUserItemBooking(item.getId(), userId);
        if (bookings.isEmpty()) {
            log.error("Нельзя оставить отзыв на вещь, которая не была в аренде");
            throw new NotFoundException("Нельзя оставить отзыв на вещь, которая не была в аренде");
        }
        return user;
    }

    private User checkUserExists(Long userId) {
        Optional<User> user = userStorage.find(userId);
        if (user.isEmpty()) {
            log.error("Пользователь не найден (ID={})", userId);
            throw new NotFoundException("Пользователь не найден (ID={})");
        }
        return user.get();
    }

    private void checkComment(RequestCommentDto request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            log.error("Текст комментария не может быть пустым");
            throw new ValidationException("Текст комментария не может быть пустым");
        }
    }
}
