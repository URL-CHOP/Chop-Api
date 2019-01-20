package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.repository.ShortenRepository;

import org.junit.jupiter.api.DisplayName;

import me.nexters.chop.repository.StatisticsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ShortenServiceTest {
    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private ShortenRepository shortenRepository;

    @InjectMocks
    private ShortenService shortenService;

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
    public void save_success() {
        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        given(shortenRepository.save(url)).willReturn(url);
        given(shortenRepository.findUrlByOriginUrl(originUrl)).willReturn(url);

        UrlRequestDto dto = UrlRequestDto.builder()
                .originUrl(originUrl)
                .build();

        Url returnedUrl = shortenService.save(dto);

        assertThat(returnedUrl.getOriginUrl()).isEqualTo(originUrl);
        verify(shortenRepository, times(1)).getMaxId();
        verify(shortenRepository, times(1)).findUrlByOriginUrl(originUrl);
    }

    @Test
    public void saveEmptyUrl_isFailed_ThrowException() {
        UrlRequestDto dto = UrlRequestDto.builder()
                .build();

        assertThrows(NullPointerException.class, () -> shortenService.save(dto));
    }

    @Test
<<<<<<< HEAD
    @DisplayName("originUrl 을 업데이트 이후 count 테스트")
    public void urlCountPlus() {
        int count = statisticsRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        statisticsRepository.updateTotalCount("https://namu.wiki/w/%EC%B9%98%ED%82%A8");
        assertEquals(count+1 , statisticsRepository.findByOriginUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8"));
=======
    public void totalCountPlus_IsSuccess() {
        shortenService.totalCountPlus(originUrl);

        verify(statisticsRepository, times(1)).updateTotalCount(originUrl);
    }

    @Test
    void findMaxIdFromDatabase_isSuccess() {
        given(shortenRepository.getMaxId()).willReturn(1L);

        long maxId = shortenService.findMaxIdFromDatabase();

        assertThat(maxId).isEqualTo(2);
        verify(shortenRepository, times(1)).getMaxId();
>>>>>>> dbb7dcdcac2d05a6fd881d41e4238e856de39fca
    }
}
