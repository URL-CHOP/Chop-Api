package me.nexters.chop.service;

import me.nexters.chop.repository.ShortenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taehoon.choi 2019-01-11
 */

@Service
public class StatisticsService {

    private ShortenRepository shortenRepository;

    public StatisticsService(ShortenRepository shortenRepository) {
        this.shortenRepository = shortenRepository;
    }

    public List<String> statisticsInsert(String shortUrl, String host, String userAgent) {

        List<String> statisticsInformation = new ArrayList<>();
        statisticsInformation.add(String.valueOf( shortenRepository.findByShortUrl(shortUrl)) );
        statisticsInformation.add(host);
        statisticsInformation.add(userAgent);
        return statisticsInformation;
    }

}
