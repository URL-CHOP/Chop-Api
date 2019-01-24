package me.nexters.chop.api;

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
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/chop/v1/cout")
    public ResponseEntity<StatsMainResponseDto> mainRequest() {
        long globalCount = statisticsService.getGlobalCount();

        StatsMainResponseDto dto = StatsMainResponseDto.builder()
                .globalCount(globalCount)
                .build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
