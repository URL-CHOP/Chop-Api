package me.nexters.chop.repository.url;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.url.projections.TotalCountOnly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @author taehoon.choi
 */
public interface StatisticsRepository extends CrudRepository<Url, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Url set totalCount=totalCount+1 where originUrl = ?1")
    void updateTotalCount(String originUrl);

    Collection<TotalCountOnly> findByShortUrl(String shortUrl);
}
