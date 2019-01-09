package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.RedirectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    private String expectedLongUrl;

    @BeforeEach
    public void setUp() {
        shortenUrl = "a";
        expectedLongUrl = "https://namu.wiki/w/%EC%B9%98%ED%82%A8";
    }

    @Test
    public void findUrlByShortUrl_isSuccess() {
        Url returnedUrl = redirectRepository.findUrlByShortUrl(shortenUrl);
        assertEquals(expectedLongUrl, returnedUrl.getLongUrl());
    }
}
