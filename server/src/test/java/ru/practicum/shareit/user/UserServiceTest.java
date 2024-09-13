package ru.practicum.shareit.user;

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
public class UserServiceTest {
    private final UserService service;
    private final EntityManager em;

    @Test
    void testCreateUser() {
        UserDto dto = new UserDto(null, "user@mail.ru", "User Test");
        service.create(dto);

        TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email", User.class);
        User user = query.setParameter("email", dto.getEmail()).getSingleResult();

        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo(dto.getName()));
        assertThat(user.getEmail(), equalTo(dto.getEmail()));
    }

    @Test
    void testCreateUserWithSameEmail() {
        UserDto user1 = new UserDto(null, "user@mail.ru", "Helen");
        service.create(user1);

        UserDto user2 = new UserDto(null, "user@mail.ru", "Maria");
        assertThrows(InternalServerException.class, () -> {
            service.create(user2);
        }, "Ожидалось исключение: Не удалось создать пользователя: такой email существует");
    }
}
