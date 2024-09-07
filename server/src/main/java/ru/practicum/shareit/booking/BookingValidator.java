package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.base.exception.NotFoundException;
import ru.practicum.shareit.base.exception.ValidationException;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Optional;

@Service
@Slf4j
public class BookingValidator {
    @Qualifier("dbItemStorage")
    private final ItemStorage itemStorage;
    @Qualifier("dbUserStorage")
    private final UserStorage userStorage;
    @Qualifier("dbBookingStorage")
    private final BookingStorage bookingStorage;

    @Autowired
    BookingValidator(@Qualifier("dbItemStorage") ItemStorage itemStorage,
                     @Qualifier("dbUserStorage") UserStorage userStorage,
                     @Qualifier("dbBookingStorage") BookingStorage bookingStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
        this.bookingStorage = bookingStorage;
    }

    public Booking checkByUser(Long userId, RequestBookingDto request) {
        User user = checkAndGetUser(userId);
        Item item = checkAndGetItem(request.getItemId());
        checkItemAvailable(item);
        Booking booking = BookingMapper.requestToModel(request);
        booking.setUser(user);
        booking.setItem(item);
        return booking;
    }

    public Booking checkByOwner(Long ownerId, Long bookingId) {
        Booking booking = checkAndGetBooking(bookingId);
        checkItemsOwner(booking.getItem(), ownerId);
        return booking;
    }

    public Booking checkByUserOrOwner(Long userId, Long bookingId) {
        Booking booking = checkAndGetBooking(bookingId);
        checkItemsOwnerOrUser(booking, userId);
        return booking;
    }

    public Booking checkAndGetBooking(Long bookingId) {
        Optional<Booking> booking = bookingStorage.find(bookingId);
        if (booking.isEmpty()) {
            log.error("Бронь не найдена (ID={})", bookingId);
            throw new NotFoundException("Бронь не найдена");
        }
        return booking.get();
    }

    public User checkAndGetUser(Long userId) {
        Optional<User> user = userStorage.find(userId);
        if (user.isEmpty()) {
            log.error("Пользователь не найден (ID={})", userId);
            throw new NotFoundException("Пользователь не найден");
        }
        return user.get();
    }

    public Item checkAndGetItem(Long itemId) {
        Optional<Item> item = itemStorage.find(itemId);
        if (item.isEmpty()) {
            log.error("Вещь не найдена (ID={})", itemId);
            throw new NotFoundException("Вещь не найдена");
        }
        return item.get();
    }

    private void checkItemsOwner(Item item, Long ownerId) {
        if (!item.getOwnerId().equals(ownerId)) {
            log.error("Вещь вам не принадлежит (ID={})", item.getId());
            throw new ValidationException("Вещь вам не принадлежит");
        }
    }

    private void checkItemsOwnerOrUser(Booking booking, Long userId) {
        if (!booking.getItem().getOwnerId().equals(userId) && !booking.getUser().getId().equals(userId)) {
            log.error("Нет доступа к бронированию (ID={})", booking.getId());
            throw new ValidationException("Нет доступа к бронированию");
        }
    }

    private void checkItemAvailable(Item item) {
        if (!item.isAvailable()) {
            log.error("Вещь находиться в аренде (ID={})", item.getId());
            throw new ValidationException("Вещь находиться в аренде");
        }
    }
}
