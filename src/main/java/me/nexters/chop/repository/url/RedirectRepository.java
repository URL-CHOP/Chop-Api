package me.nexters.chop.repository.url;

import me.nexters.chop.domain.url.Url;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author junho.park
 */
public interface RedirectRepository extends JpaRepository<Url, Long> {
    Url findUrlByShortUrl(String shortenUrl);
}
