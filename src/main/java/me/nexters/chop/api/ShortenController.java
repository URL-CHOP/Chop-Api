package me.nexters.chop.api;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.ShortenService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junho.park
 */
@RestController
public class ShortenController {

    private final ShortenService shortenService;

    public ShortenController(ShortenService shortenService) {
        this.shortenService = shortenService;
    }

    @PostMapping("/chop/v1/shorten")
    public UrlResponseDto shortenUrl(@Valid @RequestBody UrlRequestDto dto) {
        Url responseUrl = shortenService.save(dto);

        return UrlResponseDto.builder()
                .shortUrl(responseUrl.getShortUrl())
                .originUrl(responseUrl.getOriginUrl())
                .build();
    }
}
