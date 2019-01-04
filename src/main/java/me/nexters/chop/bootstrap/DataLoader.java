package me.nexters.chop.bootstrap;

import me.nexters.chop.dto.url.UrlSaveRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author junho.park
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final ShortenRepository shortenRepository;

    public DataLoader(ShortenRepository shortenRepository) {
        this.shortenRepository = shortenRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        UrlSaveRequestDto sampleDto = UrlSaveRequestDto.builder()
                .longUrl("looonggg")
                .shortUrl("aa")
                .build();

        shortenRepository.save(sampleDto.toEntity());
    }
}
