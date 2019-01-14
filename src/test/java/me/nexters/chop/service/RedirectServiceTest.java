package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.RedirectRepository;
import me.nexters.chop.repository.ShortenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedirectServiceTest {

    @Autowired
    private RedirectRepository redirectRepository;

    private String shortenUrl;
    private String expectedOriginUrl;

    @BeforeEach
    public void setUp() {
        shortenUrl = "a";
        expectedOriginUrl = "https://namu.wiki/w/%EC%B9%98%ED%82%A8";
    }

    @Test
    @Transactional
    public void findUrlByShortUrl_isSuccess() {
        Url returnedUrl = redirectRepository.findUrlByShortUrl(shortenUrl);

        assertEquals(expectedOriginUrl, returnedUrl.getOriginUrl());
    }
}
