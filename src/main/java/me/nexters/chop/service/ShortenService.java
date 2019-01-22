package me.nexters.chop.service;

import me.nexters.chop.domain.statistics.Statistics;
import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import me.nexters.chop.repository.StatisticsRepository;
import me.nexters.chop.statistics.UrlToStatisticsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author junho.park
 */
@Service
public class ShortenService {
    private static final int BASE62 = 62;

    @Value("${string.base62}")
    private String base62String;

    private final ShortenRepository shortenRepository;
    private final StatisticsRepository statisticsRepository;
    private final UrlToStatisticsRepository urlToStatisticsRepository;

    public ShortenService(ShortenRepository shortenRepository, StatisticsRepository statisticsRepository, UrlToStatisticsRepository urlToStatisticsRepository) {
        this.shortenRepository = shortenRepository;
        this.statisticsRepository = statisticsRepository;
        this.urlToStatisticsRepository = urlToStatisticsRepository;
    }

    public String base62Encode(int inputNumber) {
        char[] table = base62String.toCharArray();
        StringBuilder sb = new StringBuilder();

        while (inputNumber > 0) {
            sb.append(table[inputNumber % BASE62]);
            inputNumber /= BASE62;
        }

        return sb.toString();
    }

    @Transactional
    public Url save(UrlRequestDto dto) {
        int hashNumber = findMaxIdFromDatabase();
        String originUrl = dto.getOriginUrl().trim();

        Url maybeUrl = shortenRepository.findUrlByOriginUrl(originUrl);

        if (maybeUrl != null) {
            return maybeUrl;
        }

        Url url = Url.builder()
                .originUrl(originUrl)
                .shortUrl(base62Encode(hashNumber))
                .build();

        return shortenRepository.save(url);
    }

    @Transactional(readOnly = true)
    public int findMaxIdFromDatabase() {
        return (int) (shortenRepository.getMaxId() + 1);
    }

    @Transactional
    public void totalCountPlus(String longUrl) {
        statisticsRepository.updateTotalCount(longUrl);
    }

    @Transactional
    public void statisticsInsert(String referer, String shortenUrl) {
        Statistics statistics = Statistics.builder()
                                    .clickTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                                    .referer(referer)
                                    .shortUrl(shortenUrl)
                                    .build();

        urlToStatisticsRepository.save(statistics);
    }

}
