package me.nexters.chop.statistics;

import me.nexters.chop.domain.statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author taehoon.choi 2019-01-21
 */
public interface UrlToStatisticsRepository extends JpaRepository<Statistics,Long> {
}
