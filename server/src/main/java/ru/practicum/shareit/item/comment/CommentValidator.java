package ru.practicum.shareit.item.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.comment.dto.RequestCommentDto;
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
        Collection<Booking> bookings = bookingStorage.findApprovedUserItemPastBooking(item.getId(), userId);
        if (bookings.isEmpty()) {
            log.error("Нельзя оставить отзыв на вещь, которая не была в аренде или срок аренды еще не окончен");
            throw new ValidationException("Нельзя оставить отзыв на вещь, которая не была в аренде или срок аренды еще не окончен");
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
}
