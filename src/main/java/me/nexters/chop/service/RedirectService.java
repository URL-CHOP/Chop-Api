package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.RedirectRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * @author junho.park
 */
@Service
public class RedirectService {
    private final RedirectRepository redirectRepository;

    public RedirectService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public Url redirect(String shortenUrl) {
        Url url = redirectRepository.findUrlByShortUrl(shortenUrl);

        if (url == null) {
            throw new EntityNotFoundException("nexters.me/" + shortenUrl+ " 는 등록되지 않은 URL 입니다.");
        }

        return url;
    }
}
