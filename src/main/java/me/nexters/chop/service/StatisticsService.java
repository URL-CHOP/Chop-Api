package me.nexters.chop.service;

import lombok.RequiredArgsConstructor;
import me.nexters.chop.repository.url.GlobalCountRepository;
import me.nexters.chop.repository.url.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author taehoon.choi 2019-01-11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final GlobalCountRepository globalCountRepository;

    public long getGlobalCount() {
        return globalCountRepository.findById(1L).get().getGlobalCount();
    }

    @Transactional
    public void totalCountPlus(String longUrl) {
        statisticsRepository.updateTotalCount(longUrl);
    }
}
