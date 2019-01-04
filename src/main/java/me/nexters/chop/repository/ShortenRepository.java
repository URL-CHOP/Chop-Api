package me.nexters.chop.repository;

import me.nexters.chop.domain.url.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author junho.park
 */
public interface ShortenRepository extends JpaRepository<Url, Long> {

    @Query("SELECT MAX(id) FROM Url")
    Long findByMaxId();
}