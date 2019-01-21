package me.nexters.chop.api;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.RedirectService;
import me.nexters.chop.service.ShortenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * @author junho.park
 */
@RestController
public class RedirectController {

    private final RedirectService redirectService;
    private final ShortenService shortenService;

    public RedirectController(RedirectService redirectService, ShortenService shortenService) {
        this.redirectService = redirectService;
        this.shortenService = shortenService;
    }

    @GetMapping("/{shortenUrl}")
    public ResponseEntity<UrlResponseDto> redirect(@PathVariable("shortenUrl") String shortenUrl) {
        Url url = redirectService.redirect(shortenUrl);

        String originUrl = url.getOriginUrl();

        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortenUrl)
                .build();

        shortenService.totalCountPlus(originUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));

        return new ResponseEntity<>(responseDto, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
