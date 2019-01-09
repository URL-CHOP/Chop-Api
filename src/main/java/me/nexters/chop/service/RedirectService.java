package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.RedirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author junho.park
 */
@Service
public class RedirectService {
    @Autowired
    private RedirectRepository redirectRepository;

    public Url redirect(String shortenUrl) {
        return redirectRepository.findUrlByShortUrl(shortenUrl);
    }
}
