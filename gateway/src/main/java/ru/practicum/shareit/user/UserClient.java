package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.request.UserRequest;

import java.util.Map;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> find(Long id) {
        Map<String, Object> parameters = Map.of(
                "id", id
        );
        return get("/{id}", null, parameters);
    }

    public ResponseEntity<Object> create(UserRequest request) {
        return post("", request);
    }

    public ResponseEntity<Object> update(Long id, UserRequest request) {
        return patch("/" + id, request);
    }

    public void delete(Long id) {
        Map<String, Object> parameters = Map.of(
                "id", id
        );
        delete("/{id}", null, parameters);
    }
}
