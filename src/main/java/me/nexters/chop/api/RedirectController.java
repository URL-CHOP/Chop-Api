package me.nexters.chop.api;

import java.net.URI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.nexters.chop.service.ShortenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
@Api(description = "리다이렉트 api")
public class RedirectController {
    
    private final RedirectService redirectService;
    private final StatisticsService statisticsService;
    private final ShortenService shortenService;

    @GetMapping("/{shortenUrl}")
    @ApiOperation(value = "Url 리다이렉트", notes = "단축 Url을 리다이렉트 해준다", response = UrlResponseDto.class)
    public ResponseEntity<UrlResponseDto> redirect(@PathVariable("shortenUrl") String shortenUrl
                                                 , @RequestHeader(value = "Referer",required = false) String referer){
        Url url = redirectService.redirect(shortenUrl);

        String originUrl = url.getOriginUrl();

        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortenUrl)
                .build();

        shortenService.statisticsInsert(referer,shortenUrl);
        statisticsService.totalCountPlus(originUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));

        return new ResponseEntity<>(responseDto, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
