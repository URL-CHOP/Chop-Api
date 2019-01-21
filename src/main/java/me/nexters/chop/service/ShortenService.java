package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import me.nexters.chop.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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

    public ShortenService(ShortenRepository shortenRepository, StatisticsRepository statisticsRepository) {
        this.shortenRepository = shortenRepository;
        this.statisticsRepository = statisticsRepository;
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
    public Url shorten(UrlRequestDto dto) {
        String originUrl = dto.getOriginUrl().trim();

        return Optional.ofNullable(shortenRepository
                .findUrlByOriginUrl(originUrl)).orElseGet(() -> saveUrl(originUrl));
    }

    private Url saveUrl(String originUrl) {
        int hashNumber = findMaxIdFromDatabase();

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
}
