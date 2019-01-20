package me.nexters.chop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.dto.url.UrlResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class ShortenControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    ShortenController shortenController;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Value("${string.base62matchPattern}")
    private String base62matchPattern;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(shortenController).build();
    }

    @Test
    public void shortenUrl_IsSuccess() throws Exception {
        UrlRequestDto requestDto = UrlRequestDto.builder()
                .originUrl(originUrl)
                .build();

        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        given(shortenController.shortenUrl(any(UrlRequestDto.class)))
                .willReturn(new ResponseEntity<>(responseDto, HttpStatus.OK));

        mockMvc.perform(post("/chop/v1/shorten")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.originUrl", is(originUrl)))
                .andExpect(jsonPath("$.shortUrl", is(shortUrl)))
                .andDo(print());
    }
}