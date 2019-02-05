package me.nexters.chop.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.RedirectService;

/**
 * @author junho.park
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "리다이렉트 api")
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("/{shortenUrl}")
    @ApiOperation(value = "Url 리다이렉트", notes = "단축 Url을 리다이렉트 해준다", response = UrlResponseDto.class)
    public ResponseEntity<UrlResponseDto> redirect(@PathVariable("shortenUrl") String shortenUrl){
        Url url = redirectService.redirect(shortenUrl);

        String originUrl = url.getOriginUrl();

        UrlResponseDto responseDto = UrlResponseDto.builder()
                .originUrl(originUrl)
                .shortUrl(shortenUrl)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));

        return new ResponseEntity<>(responseDto, headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
