package me.nexters.chop.bootstrap;

import me.nexters.chop.dto.url.UrlSaveRequestDto;
import me.nexters.chop.repository.ShortenRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author junho.park
 */
public class DataLoader implements ApplicationRunner {

    private final ShortenRepository shortenRepository;

    public DataLoader(ShortenRepository shortenRepository) {
        this.shortenRepository = shortenRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        UrlSaveRequestDto sampleDto = UrlSaveRequestDto.builder()
                .longUrl("https://namu.wiki/w/%EC%B9%98%ED%82%A8")
                .shortUrl("a")
                .build();

        shortenRepository.save(sampleDto.toEntity());
    }
}
