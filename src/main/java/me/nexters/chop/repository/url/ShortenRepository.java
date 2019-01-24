package me.nexters.chop.repository.url;

import me.nexters.chop.domain.url.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author junho.park
 */
public interface ShortenRepository extends JpaRepository<Url, Long> {
    @Query("SELECT MAX(id) FROM Url")
    long getMaxId();

    Url findUrlByOriginUrl(String originUrl);
}
