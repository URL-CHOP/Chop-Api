package me.nexters.chop.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.repository.url.ShortenRepository;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class ShortenServiceTest {
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
        assertThat(shortUrl.matches(base62matchPattern)).isTrue();
    }

    @Test
    public void shorten_Success_IfOriginUrlAlreadyExists() {
        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        given(shortenRepository.findUrlByOriginUrl(originUrl)).willReturn(url);

        UrlRequestDto dto = UrlRequestDto.builder()
                .originUrl(originUrl)
                .build();

        Url returnedUrl = shortenService.shorten(dto);

        assertThat(returnedUrl.getOriginUrl()).isEqualTo(originUrl);
        verify(shortenRepository, times(1)).findUrlByOriginUrl(originUrl);
    }

    @Test // TODO
    public void shorten_Success_IfOriginUrlNotExists() {
    }

    @Test
    public void saveEmptyUrl_IsFailed_ThrowException() {
        UrlRequestDto dto = UrlRequestDto.builder()
                .build();

        assertThrows(NullPointerException.class, () -> shortenService.shorten(dto));
    }
}
