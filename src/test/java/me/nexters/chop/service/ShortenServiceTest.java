package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlSaveRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class ShortenServiceTest {

    @Autowired
    private ShortenRepository shortenRepository;

    @Value("${string.base62matchPattern}")
    private String base62matchPattern;

    @Value("${string.longUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Test
    public void base62Encode_SatisfyRegex() {
        assertEquals(true, shortUrl.matches(base62matchPattern));
    }

    @Test
    public void save_SaveSuccess() {
        UrlSaveRequestDto requestDto = UrlSaveRequestDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        Url url = shortenRepository.save(requestDto.toEntity());

        assertEquals(url.getOriginUrl(), originUrl);
    }

    @Test
    public void save_EmptyLongUrl_DataIntegrityViolationException() {
        UrlSaveRequestDto requestDto = UrlSaveRequestDto.builder()
                .build();

        assertThrows(DataIntegrityViolationException.class, () ->
            shortenRepository.save(requestDto.toEntity())
        );
    }

    @Test
    public void urlCountPlus() {
        int count = shortenRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        shortenRepository.updateTotalCount("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        assertEquals(count+1 , shortenRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8"));
    }
}
