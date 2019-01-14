package me.nexters.chop.api;

import me.nexters.chop.dto.stats.StatsMainResponseDto;
import me.nexters.chop.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author taehoon.choi 2019-01-09
 */

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics/{shortUrl}")
    public List<String> httpHeader(@PathVariable("shortUrl") String shortUrl
                             , @RequestHeader(value = "Host") String host
                             , @RequestHeader(value = "User-Agent", defaultValue = "myBrowser") String userAgent
                             , @RequestHeader(value = "Referer",required = false) String referer) {
        return statisticsService.statisticsInsert(shortUrl, host, userAgent);
    }

    @GetMapping("/chop/v1/global-count")
    public StatsMainResponseDto mainRequest() {
        int globalCount = statisticsService.getGlobalCount();

        StatsMainResponseDto dto = StatsMainResponseDto.builder()
                .globalCount(globalCount)
                .build();

        return dto;
    }
}
