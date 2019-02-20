package me.nexters.chop.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.stats.StatsMainResponseDto;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.dto.url.UrlResponseDto;
import me.nexters.chop.service.ShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junho.park
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "단축 api")
public class ShortenController {

	private final ShortenService shortenService;

	@CrossOrigin(origins = "*")
	@PostMapping("/api/v1/shorten")
	@ApiOperation(value = "Url 단축", notes = "Url을 단축하여 반환한다", response = UrlRequestDto.class)
	public ResponseEntity<UrlResponseDto> shortenUrl(@Valid @RequestBody UrlRequestDto dto) {
		Url responseUrl = shortenService.shorten(dto);

		shortenService.updateTotalUrlCount();

		UrlResponseDto responseDto = UrlResponseDto.builder()
				.shortUrl("nexters.me/" + responseUrl.getShortUrl())
				.originUrl(responseUrl.getOriginUrl())
				.build();

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/v1/count")
	@ApiOperation(value = "단축 Url 갯수 반환", notes = "여태까지 단축된 Url의 갯수를 반환한다", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsMainResponseDto> globalCount() {
		long globalCount = shortenService.getGlobalCount();

		StatsMainResponseDto dto = StatsMainResponseDto.builder()
			.globalCount(globalCount)
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/error")
	public ResponseEntity error() {
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
