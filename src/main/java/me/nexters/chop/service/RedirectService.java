package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.RedirectRepository;
import org.springframework.stereotype.Service;

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
        return redirectRepository.findUrlByShortUrl(shortenUrl);
    }
}
