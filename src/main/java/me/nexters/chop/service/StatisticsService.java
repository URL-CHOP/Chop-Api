package me.nexters.chop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.chop.repository.StatisticsRepository;

/**
 * @author taehoon.choi 2019-01-11
 */
// TODO 통계 작업은 삭제 후 통계 서버로 이전해야 함

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public List<String> statisticsInsert(String shortUrl, String host, String userAgent) {

        List<String> statisticsInformation = new ArrayList<>();
        statisticsInformation.add(String.valueOf( statisticsRepository.findByShortUrl(shortUrl)) );
        statisticsInformation.add(host);

        Pattern pattern = Pattern.compile("(?<os>\\([a-zA-Z]*)");
        Matcher matcher = pattern.matcher(userAgent);
        if (matcher.find())
            statisticsInformation.add(matcher.group("os").replace("(",""));

        return statisticsInformation;
    }

    public long getGlobalCount() {
        return statisticsRepository.count();
    }

    public void totalCountPlus(String longUrl) {
        statisticsRepository.updateTotalCount(longUrl);
    }
}
