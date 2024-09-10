package ru.practicum.shareit.booking;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.ShareItServer;
import ru.practicum.shareit.base.DateTimeHelper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = ShareItServer.class)
public class BookingServiceTest {
    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;
    private final EntityManager em;

    @Test
    void testCreateBooking() {
        UserDto owner = createUser("owner@mail.ru", "Owner");
        ItemDto item = createItem(owner);

        UserDto user = createUser("user@mail.ru", "User");
        BookingDto bookingDto = createBooking(user, item, "2024-09-01T10:00:00", "2024-09-02T10:00:00");

        TypedQuery<Booking> query = em.createQuery("Select b from Booking b where b.id = :id", Booking.class);
        Booking booking = query.setParameter("id", bookingDto.getId()).getSingleResult();

        assertThat(booking.getId(), notNullValue());
        assertThat(booking.getItem().getId(), equalTo(item.getId()));
        assertThat(booking.getItem().getName(), equalTo(item.getName()));
        assertThat(booking.getStart(), equalTo(DateTimeHelper.toInstant(bookingDto.getStart())));
        assertThat(booking.getEnd(), equalTo(DateTimeHelper.toInstant(bookingDto.getEnd())));
    }

    private UserDto createUser(String email, String name) {
        UserDto user = new UserDto(null, email, name);
        return userService.create(user);
    }

    private ItemDto createItem(UserDto user) {
        RequestItemDto requestItemDto = new RequestItemDto();
        requestItemDto.setName("Скарлетт О’Хара");
        requestItemDto.setDescription("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
        requestItemDto.setAvailable(true);
        return itemService.create(user.getId(), requestItemDto);
    }

    private BookingDto createBooking(UserDto user, ItemDto item, String start, String end) {
        RequestBookingDto requestBookingDto = new RequestBookingDto(item.getId(), start, end);
        return bookingService.create(user.getId(), requestBookingDto);
    }

    private BookingDto createBooking(String start, String end) {
        UserDto owner = createUser("owner@mail.ru", "Owner");
        ItemDto item = createItem(owner);
        UserDto user = createUser("user@mail.ru", "User");
        RequestBookingDto requestBookingDto = new RequestBookingDto(item.getId(), start, end);
        return bookingService.create(user.getId(), requestBookingDto);
    }
}
