package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author junho.park
 */
@Service
public class ShortenService {
    private static final int BASE62 = 62;

    @Value("${string.base62}")
    private String base62String;

    private final ShortenRepository shortenRepository;

    public ShortenService(ShortenRepository shortenRepository) {
        this.shortenRepository = shortenRepository;
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
        String originUrl = dto.getOriginUrl();

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
}
