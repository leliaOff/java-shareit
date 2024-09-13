package ru.practicum.shareit.request;

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
import ru.practicum.shareit.base.exception.InternalServerException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = ShareItServer.class)
public class RequestServiceTest {
    private final RequestService service;
    private final UserService userService;
    private final EntityManager em;

    @Test
    void testCreateRequest() {
        UserDto user = new UserDto(null, "user@mail.ru", "User Test");
        user = userService.create(user);

        RequestRequestDto requestRequestDto = new RequestRequestDto("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
        RequestDto requestDto = service.create(user.getId(), requestRequestDto);

        TypedQuery<Request> query = em.createQuery("Select r from Request r where r.id = :id", Request.class);
        Request request = query.setParameter("id", requestDto.getId()).getSingleResult();

        assertThat(request.getId(), notNullValue());
        assertThat(request.getDescription(), equalTo(requestRequestDto.getDescription()));
    }
}
