package me.nexters.chop.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nexters.chop.domain.url.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
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

    @Value("${string.originUrl}")
    private String originUrl;

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
    @Transactional
    public void saveUrl_Success() throws Exception {
        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        String result = mockMvc.perform(post("/chop/v1/shorten")
                .contentType("application/json;charset=UTF-8")
                .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Url responseUrl = objectMapper.readValue(result, Url.class);


        assertEquals(originUrl, url.getOriginUrl());
        assertEquals(true, url.getShortUrl().matches(base62matchPattern));

        assertEquals(originUrl, responseUrl.getOriginUrl());
        assertEquals(true, responseUrl.getShortUrl().matches(base62matchPattern));

    }
}