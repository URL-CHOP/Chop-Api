package me.nexters.chop.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.api.grpc.ChopGrpcClient;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author junho.park
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "리다이렉트 api")
@Slf4j
public class RedirectController {

    private final RedirectService redirectService;
    private final ChopGrpcClient grpcClient;


	@CrossOrigin(origins = "*")
	@GetMapping("/{shortenUrl}")
	@ApiOperation(value = "Url 리다이렉트", notes = "단축 Url을 리다이렉트 해준다", response = UrlResponseDto.class)
	public ResponseEntity<UrlResponseDto> redirect(@PathVariable("shortenUrl") String shortenUrl,
		@RequestHeader(value = "Referer", required = false, defaultValue = "none") String referer,
		@RequestHeader(value = "User-Agent", defaultValue = "myBrowser") String userAgent) {
		Url url = redirectService.redirect(shortenUrl);

		String originUrl = url.getOriginUrl();

		log.info("user-agent : {}", userAgent);

		UrlResponseDto responseDto = UrlResponseDto.builder()
			.originUrl(originUrl)
			.shortUrl(shortenUrl)
			.build();

		// gRPC 비동기 호출
		grpcClient.insertStatsToStatsServer(shortenUrl, referer, userAgent);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(originUrl));
		headers.setCacheControl(CacheControl.noStore());

		return new ResponseEntity<>(responseDto, headers, HttpStatus.MOVED_PERMANENTLY);
	}
}
