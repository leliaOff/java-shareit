package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.request.CommentRequest;
import ru.practicum.shareit.item.request.ItemRequest;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllItems(Long ownerId) {
        return get("", ownerId);
    }

    public ResponseEntity<Object> search(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}", null, parameters);
    }

    public ResponseEntity<Object> find(Long id) {
        Map<String, Object> parameters = Map.of(
                "id", id
        );
        return get("/{id}", null, parameters);
    }

    public ResponseEntity<Object> create(Long ownerId, ItemRequest request) {
        return post("", ownerId, request);
    }

    public ResponseEntity<Object> update(Long ownerId, Long id, ItemRequest request) {
        return patch("/" + id, ownerId, request);
    }

    public ResponseEntity<Object> delete(Long ownerId, Long id) {
        return delete("/" + id, ownerId);
    }

    public ResponseEntity<Object> createComment(Long id, Long userId, CommentRequest request) {
        return post("/" + id + "/comment", userId, request);
    }
}
