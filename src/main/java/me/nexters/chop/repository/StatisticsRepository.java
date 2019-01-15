package me.nexters.chop.repository;

import me.nexters.chop.domain.url.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author taehoon.choi
 */
public interface StatisticsRepository extends JpaRepository<Url, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Url set totalCount=totalCount+1 where originUrl = ?1")
    void updateTotalCount(String originUrl);

    @Query("select totalCount from Url where originUrl = ?1 ")
    int findByOriginUrl(String originUrl);

    @Query("select totalCount from Url where shortUrl = ?1 ")
    int findByShortUrl(String shortUrl);
}
