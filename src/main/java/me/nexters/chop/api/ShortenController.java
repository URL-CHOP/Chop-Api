package me.nexters.chop.api;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.service.ShortenService;
import org.springframework.web.bind.annotation.*;

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
    public Url shortenUrl(@RequestBody UrlRequestDto dto) {
        return shortenService.save(dto);
    }
}
