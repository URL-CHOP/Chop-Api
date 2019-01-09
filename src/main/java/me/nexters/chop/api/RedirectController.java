package me.nexters.chop.api;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.service.RedirectService;
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

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/{shortenUrl}")
    public ResponseEntity<Url> redirect(@PathVariable String shortenUrl) {
        Url url = redirectService.redirect(shortenUrl);
        String longUrl = url.getLongUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));

        return new ResponseEntity(url, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
