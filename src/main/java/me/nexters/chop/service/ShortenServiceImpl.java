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
public class ShortenServiceImpl implements ShortenService {
    private static final int BASE62 = 62;

    @Value("${string.base62}")
    private String base62String;

    @Autowired
    private ShortenRepository shortenRepository;

    @Override
    public String base62Encode(int inputNumber) {
        char[] table = base62String.toCharArray();
        StringBuffer sb = new StringBuffer();

        while(inputNumber > 0) {
            sb.append(table[inputNumber % BASE62]);
            inputNumber /= BASE62;
        }

        return sb.toString();
    }

    @Override
    @Transactional
    public Url saveUrl(UrlSaveRequestDto urlSaveRequestDto) {
        int maxId = findMaxIdFromDatabase();
        urlSaveRequestDto.setShortUrl(base62Encode(maxId));

        return shortenRepository.save(urlSaveRequestDto.toEntity());
    }

    @Override
    @Transactional(readOnly = true)
    public int findMaxIdFromDatabase() {
        return (int) (shortenRepository.findByMaxId() + 1);
    }
}
