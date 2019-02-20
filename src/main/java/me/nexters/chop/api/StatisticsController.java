package me.nexters.chop.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import me.nexters.chop.dto.stats.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.nexters.chop.api.grpc.ChopGrpcClient;
import me.nexters.chop.config.time.TimeUtil;
import me.nexters.chop.grpc.ClickCount;
import me.nexters.chop.grpc.Platform;
import me.nexters.chop.grpc.Referer;
import me.nexters.chop.grpc.TotalCount;

/**
 * @author junho.park
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "통계 api")
public class StatisticsController {
	private final ChopGrpcClient grpcClient;

	@CrossOrigin(origins = "*")
	@GetMapping("/api/v1/urls/{shortenUrl}/platform")
	@ApiOperation(value = "해당 url platform 반환", notes = "해당 url의 mobile, browser 카운트를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsPlatformResponseDto> platformRequest(@PathVariable("shortenUrl") String shortenUrl) {
		Platform platform = grpcClient.getPlatformStats(shortenUrl);

		StatsPlatformResponseDto dto = StatsPlatformResponseDto.builder()
			.mobile(platform.getMobile())
			.browser(platform.getBrowser())
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/v1/urls/{shortenUrl}/referer")
	@ApiOperation(value = "해당 url referer 반환", notes = "해당 url의 referer 카운트를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<List<StatsRefererResponseDto>> refererRequest(@PathVariable("shortenUrl") String shortenUrl) {
		List<Referer> referers = grpcClient.getRefererStats(shortenUrl);

		List<StatsRefererResponseDto> statsRefererResponse = referers.stream()
			.map(referer -> StatsRefererResponseDto.builder()
				.referer(referer.getReferer())
				.count(referer.getCount())
				.build())
			.collect(Collectors.toList());

		return new ResponseEntity<>(statsRefererResponse, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/v1/urls/{shortenUrl}/totalcount")
	@ApiOperation(value = "해당 url 총 클릭수 반환", notes = "해당 url의 총 클릭수를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<StatsTotalResponseDto> totalCountRequest(@PathVariable("shortenUrl") String shortenUrl) {
		TotalCount totalCount = grpcClient.getTotalCount(shortenUrl);

		StatsTotalResponseDto dto = StatsTotalResponseDto.builder()
			.totalCount(totalCount.getTotalCount())
			.build();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/api/v1/urls/{shortenUrl}/clickdate")
	@ApiOperation(value = "해당 url 총 클릭수 반환", notes = "해당 url의 총 클릭수를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<List<StatsClickResponseDto>> clickWeeklyCountRequest
			(@PathVariable("shortenUrl") String shortenUrl, @RequestParam(value="date")@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
		List<ClickCount> clickCounts = grpcClient.getWeeklyClickCount(shortenUrl, date);

		List<StatsClickResponseDto> response = clickCounts.stream()
			.map(clickCount -> StatsClickResponseDto.builder()
				.clickDate(TimeUtil.convertTimestampToString(clickCount.getDate()))
				.count(clickCount.getCount())
				.build())
			.collect(Collectors.toList());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// TODO month
	@GetMapping("/api/v1/urls/{shortenUrl}/clickdate/month")
	@ApiOperation(value = "해당 url 총 클릭수 반환", notes = "해당 url의 총 클릭수를 반환한다.", response = StatsMainResponseDto.class)
	public ResponseEntity<List<StatsClickResponseDto>> clickMonthlyCountRequest
			(@PathVariable("shortenUrl") String shortenUrl, @RequestParam(value="date")@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

		return null;
	}
}
