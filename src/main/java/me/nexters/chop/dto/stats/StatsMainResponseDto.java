package me.nexters.chop.dto.stats;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
@ApiModel(value = "Url 총 줄인 갯수 응답 DTO")
public class StatsMainResponseDto {
    @ApiModelProperty(notes = "총 줄여진 Url의 갯수", required = true)
    private long globalCount;

    @Builder
    public StatsMainResponseDto(long globalCount) {
        this.globalCount = globalCount;
    }
}
