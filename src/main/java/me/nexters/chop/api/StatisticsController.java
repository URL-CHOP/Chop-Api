package me.nexters.chop.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.nexters.chop.dto.stats.StatsMainResponseDto;
import me.nexters.chop.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taehoon.choi 2019-01-09
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(description = "통계 api")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/chop/v1/cout")
    @ApiOperation(value = "단축 Url 갯수 반환", notes = "여태까지 단축된 Url의 갯수를 반환한다", response = StatsMainResponseDto.class)
    public ResponseEntity<StatsMainResponseDto> mainRequest() {
        long globalCount = statisticsService.getGlobalCount();

        StatsMainResponseDto dto = StatsMainResponseDto.builder()
                .globalCount(globalCount)
                .build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
