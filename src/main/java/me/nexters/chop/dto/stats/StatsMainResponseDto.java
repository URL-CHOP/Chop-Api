package me.nexters.chop.dto.stats;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
public class StatsMainResponseDto {
    private long globalCount;

    @Builder
    public StatsMainResponseDto(long globalCount) {
        this.globalCount = globalCount;
    }
}
