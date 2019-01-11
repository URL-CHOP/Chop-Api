package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlSaveRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShortenRepository shortenRepository;

    public String base62Encode(int inputNumber) {
        char[] table = base62String.toCharArray();
        StringBuffer sb = new StringBuffer();

        while(inputNumber > 0) {
            sb.append(table[inputNumber % BASE62]);
            inputNumber /= BASE62;
        }

        return sb.toString();
    }

    @Transactional
    public Url save(String originUrl) {
        int hashNumber = findMaxIdFromDatabase();
        UrlSaveRequestDto dto = UrlSaveRequestDto.builder()
                .originUrl(originUrl)
                .shortUrl(base62Encode(hashNumber))
                .build();

        return shortenRepository.save(dto.toEntity());
    }

    @Transactional(readOnly = true)
    public int findMaxIdFromDatabase() {
        return (int) (shortenRepository.getMaxId() + 1);
    }

    @Transactional
    public void totalCountPlus(String longUrl) {
        shortenRepository.updateTotalCount(longUrl);
    }
}
