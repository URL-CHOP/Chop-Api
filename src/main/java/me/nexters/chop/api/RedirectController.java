package me.nexters.chop.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.RedirectService;
import me.nexters.chop.service.StatisticsService;

/**
 * @author junho.park
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedirectController {

    private final RedirectService redirectService;
    private final StatisticsService statisticsService;

    @GetMapping("/{shortenUrl}")
    public ResponseEntity<UrlResponseDto> redirect(@PathVariable("shortenUrl") String shortenUrl) {
        Url url = redirectService.redirect(shortenUrl);

        String originUrl = url.getOriginUrl();

        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortenUrl)
                .build();

        statisticsService.totalCountPlus(originUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));
        headers.setCacheControl(CacheControl.noStore());

        return new ResponseEntity<>(responseDto, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
