package me.nexters.chop.api;

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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class RedirectControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RedirectController redirectController;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(redirectController).build();
    }

    @Test
    public void redirect_isSuccess() throws Exception {
        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        given(redirectController.redirect(shortUrl,"https://google.com", "mobile"))
                .willReturn(new ResponseEntity<>(responseDto, HttpStatus.MOVED_PERMANENTLY));

        mockMvc.perform(get("/{shortenUrl}", shortUrl)
                .header("Referer", "https://google.com")
                .header("User-Agent", "mobile"))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}