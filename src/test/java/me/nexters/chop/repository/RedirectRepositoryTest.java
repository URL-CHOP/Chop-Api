package me.nexters.chop.repository;


import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.url.RedirectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RedirectRepositoryTest {
    @Autowired
    RedirectRepository redirectRepository;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @Test
    public void saveUrlAndFind_isSuccess() {
        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();
        redirectRepository.save(url);

        Url returnedUrl = redirectRepository.findUrlByShortUrl(shortUrl);
        assertThat(url.getOriginUrl()).isEqualTo(returnedUrl.getOriginUrl());
        assertThat(url.getShortUrl()).isEqualTo(returnedUrl.getShortUrl());
    }

    @Test
    public void saveUrlAndFindNotExists_isFailed() {

        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();
        redirectRepository.save(url);
        assertThrows(NullPointerException.class, () -> {
            Url returnedUrl = redirectRepository.findUrlByShortUrl("aa");
            returnedUrl.getOriginUrl();
        });
    }
}