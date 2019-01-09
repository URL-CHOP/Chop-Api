package me.nexters.chop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author junho.park
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ShortenControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Value("${string.longUrl}")
    private String longUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Value("${string.base62matchPattern}")
    private String base62matchPattern;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void saveUrl_Success() throws Exception {
        UrlSaveRequestDto dto = UrlSaveRequestDto.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();

        String result = mockMvc.perform(post("/chop/v1/shorten")
                .contentType("application/json;charset=UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Url url = objectMapper.readValue(result, Url.class);

        assertEquals(longUrl, url.getLongUrl());
        assertEquals(true, url.getShortUrl().matches(base62matchPattern));
    }
}