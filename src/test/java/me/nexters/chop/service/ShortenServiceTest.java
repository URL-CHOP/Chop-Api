package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.ShortenRepository;

import org.junit.jupiter.api.DisplayName;

import me.nexters.chop.repository.StatisticsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


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

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Value("${string.base62matchPattern}")
    private String base62matchPattern;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Test
    public void base62Encode_SatisfyRegex() {
        assertEquals(true, shortUrl.matches(base62matchPattern));
    }

    @Test
    @Transactional
    public void save_SaveSuccess() {

        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        Url responseUrl = shortenRepository.save(url);

        assertEquals(url.getOriginUrl(), originUrl);
        assertEquals(responseUrl.getOriginUrl(), originUrl);
    }

    @Test
    public void save_EmptyOriginUrl_DataIntegrityViolationException() {
        Url url = Url.builder()
                .build();

        assertThrows(DataIntegrityViolationException.class, () ->
            shortenRepository.save(url)
        );
    }

    @Test
    @DisplayName("originUrl 을 업데이트 이후 count 테스트")
    public void urlCountPlus() {
        int count = statisticsRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        statisticsRepository.updateTotalCount("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        assertEquals(count+1 , statisticsRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8"));
    }
}
