package me.nexters.chop.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.nexters.chop.api.grpc.ChopGrpcClient;
import me.nexters.chop.dto.stats.StatsMainResponseDto;
import me.nexters.chop.dto.stats.StatsPlatformResponseDto;
import me.nexters.chop.dto.stats.StatsRefererResponseDto;
import me.nexters.chop.dto.stats.StatsTotalResponseDto;
import me.nexters.chop.grpc.Platform;
import me.nexters.chop.grpc.Referer;
import me.nexters.chop.grpc.TotalCount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junho.park
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "통계 api")
public class StatisticsController {
	private final ChopGrpcClient grpcClient;

	@GetMapping("/chop/v1/platform/{shortenUrl}")
	@ApiOperation(value = "해당 url platform 반환", notes = "해당 url의 mobile, browser 카운트를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsPlatformResponseDto> platformRequest(@PathVariable("shortenUrl") String shortenUrl) {
		Platform platform = grpcClient.getPlatformStats(shortenUrl);

		StatsPlatformResponseDto dto = StatsPlatformResponseDto.builder()
			.shortUrl(platform.getShortUrl())
			.mobile(platform.getMobile())
			.browser(platform.getBrowser())
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/chop/v1/referer/{shortenUrl}")
	@ApiOperation(value = "해당 url referer 반환", notes = "해당 url의 referer 카운트를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsRefererResponseDto> refererRequest(@PathVariable("shortenUrl") String shortenUrl) {
		Referer referer = grpcClient.getRefererStats(shortenUrl);

		StatsRefererResponseDto dto = StatsRefererResponseDto.builder()
			.shortUrl(referer.getShortUrl())
			.referer(referer.getRefererList())
			.count(referer.getCountList())
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/chop/v1/totalcount/{shortenUrl}")
	@ApiOperation(value = "해당 url 총 클릭수 반환", notes = "해당 url의 총 클릭수를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsTotalResponseDto> totalCountRequest(@PathVariable("shortenUrl") String shortenUrl) {
		TotalCount totalCount = grpcClient.getTotalCount(shortenUrl);

		StatsTotalResponseDto dto = StatsTotalResponseDto.builder()
			.shortUrl(totalCount.getShortUrl())
			.totalCount(totalCount.getTotalCount())
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
