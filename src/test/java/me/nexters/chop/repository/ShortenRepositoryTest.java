package me.nexters.chop.repository;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.url.ShortenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ShortenRepositoryTest {
    @Autowired
    ShortenRepository shortenRepository;

    @Value("${string.originUrl}")
    private String originUrl;

    @Value("${string.shortUrl}")
    private String shortUrl;

    @BeforeEach
    void setUp() {
        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();

        shortenRepository.save(url);
    }

    @Test
    void getMaxId_isSuccess() {
        long maxId = shortenRepository.getMaxId();

        assertThat(maxId).isEqualTo(3L);
    }

    @Test
    void findUrlByOriginUrl_isSuccess() {
        Url url = shortenRepository.findUrlByOriginUrl(originUrl);

        assertThat(url.getOriginUrl()).isEqualTo(originUrl);
        assertThat(url.getShortUrl()).isEqualTo(shortUrl);
    }
}