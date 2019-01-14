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
    private int globalCount;

    @Builder
    public StatsMainResponseDto(int globalCount) {
        this.globalCount = globalCount;
    }
}
