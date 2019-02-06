package me.nexters.chop.api;

import me.nexters.chop.dto.url.UrlResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author junho.park
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RedirectControllerRestTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Test
    public void redirectRestTest() {
        ResponseEntity<UrlResponseDto> responseEntity = testRestTemplate.getForEntity("/" + shortUrl, UrlResponseDto.class);
        assertThat(responseEntity.getBody().getShortUrl()).isEqualTo(shortUrl);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.MOVED_PERMANENTLY);
    }
}
