package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = ShareItServer.class)
public class ItemServiceTest {
    private final ItemService service;
    private final UserService userService;
    private final EntityManager em;

    @Test
    void testCreateItem() {
        UserDto user = new UserDto(null, "user@mail.ru", "User Test");
        user = userService.create(user);

        RequestItemDto requestItemDto = new RequestItemDto();
        requestItemDto.setName("Скарлетт О’Хара");
        requestItemDto.setDescription("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
        requestItemDto.setAvailable(true);
        ItemDto itemDto = service.create(user.getId(), requestItemDto);

        TypedQuery<Item> query = em.createQuery("Select i from Item i where i.id = :id", Item.class);
        Item item = query.setParameter("id", itemDto.getId()).getSingleResult();

        assertThat(item.getId(), notNullValue());
        assertThat(item.getName(), equalTo(requestItemDto.getName()));
        assertThat(item.getDescription(), equalTo(requestItemDto.getDescription()));
        assertThat(item.isAvailable(), equalTo(requestItemDto.getAvailable()));
        assertThat(item.getRequestId(), nullValue());
    }
}
