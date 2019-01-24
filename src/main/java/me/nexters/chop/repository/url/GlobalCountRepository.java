package me.nexters.chop.repository.url;

import me.nexters.chop.domain.url.GlobalCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author junho.park
 */
public interface GlobalCountRepository extends JpaRepository<GlobalCount, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update GlobalCount set globalCount=globalCount+1 where id=1")
    void updateTotalCount();
}

