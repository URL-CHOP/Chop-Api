package me.nexters.chop.repository;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.projections.TotalCountOnly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @author taehoon.choi
 */
public interface StatisticsRepository extends CrudRepository<Url, Long> {

    // TODO update도 수정
    @Modifying(clearAutomatically = true)
    @Query("update Url set totalCount=totalCount+1 where originUrl = ?1")
    void updateTotalCount(String originUrl);

    Collection<TotalCountOnly> findByShortUrl(String shortUrl);

    Collection<TotalCountOnly> findByOriginUrl(String originUrl);
}
