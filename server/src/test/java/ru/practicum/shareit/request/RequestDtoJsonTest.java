package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.helpers.RequestHelper;
import ru.practicum.shareit.request.dto.RequestDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestDtoJsonTest {
    private final JacksonTester<RequestDto> json;

    private RequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = RequestHelper.getRequestDto();
    }

    @Test
    void testRequestDto() throws Exception {
        JsonContent<RequestDto> result = json.write(requestDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.userId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Скарлетт О’Хара не была красавицей, но мужчины вряд ли отдавали себе в этом отчет, если они, подобно близнецам Тарлтонам, становились жертвами ее чар. Очень уж причудливо сочетались в ее лице утонченные черты матери — местной аристократки французского происхождения — и крупные, выразительные черты отца — пышущего здоровьем ирландца");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo("2024-09-01T10:00:00");
    }
}
