package me.nexters.chop.api;

import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.dto.url.UrlResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author junho.park
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ShortenControllerRestTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Value("${string.base62matchPattern}")
    private String base62Pattern;

    private Pattern URL_REGEX;

    @BeforeEach
    void setUp() {
        URL_REGEX = Pattern.compile(base62Pattern);
    }

    @Test
    public void shortenUrlSaveRestTest() {
        UrlRequestDto requestDto = UrlRequestDto.builder()
                .originUrl(originUrl)
                .build();

        HttpEntity<UrlRequestDto> request = new HttpEntity<>(requestDto);
        ResponseEntity<UrlResponseDto> responseEntity = testRestTemplate.postForEntity("/chop/v1/shorten", request, UrlResponseDto.class);

        assertThat(responseEntity.getBody().getOriginUrl()).isEqualTo(originUrl);
        assertThat(URL_REGEX.matcher(responseEntity.getBody().getShortUrl()).matches()).isFalse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
