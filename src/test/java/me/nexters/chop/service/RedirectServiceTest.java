package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.url.RedirectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
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
public class RedirectServiceTest {
    @Mock
    private RedirectRepository redirectRepository;

    @InjectMocks
    private RedirectService redirectService;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Test
    public void redirect_IsSuccess() {
        Url url = Url.builder()
                .shortUrl(shortUrl)
                .originUrl(originUrl)
                .build();

        given(redirectRepository.findUrlByShortUrl(shortUrl)).willReturn(url);

        Url returnedUrl = redirectService.redirect(shortUrl);

        assertThat(returnedUrl.getOriginUrl()).isEqualTo(originUrl);
        assertThat(returnedUrl.getShortUrl()).isEqualTo(shortUrl);
        verify(redirectRepository, times(1)).findUrlByShortUrl(shortUrl);
    }

    @Test
    public void redirect_isFailedDueToNotExistingShortUrl_throwException() {
        String notExistingUrl = "notExists";
        given(redirectRepository.findUrlByShortUrl(notExistingUrl)).willReturn(null);

        assertThrows(EntityNotFoundException.class, () -> redirectService.redirect(notExistingUrl));
    }
}
