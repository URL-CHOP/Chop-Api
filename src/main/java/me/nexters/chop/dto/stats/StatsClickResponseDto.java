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
@ApiModel(value = "Url 클릭 횟수 응답 DTO")
public class StatsClickResponseDto {
    @ApiModelProperty(notes = "날짜", required = true)
    private String clickDate;

    @ApiModelProperty(notes = "클릭 횟수", required = true)
    private int count;

    @Builder
    public StatsClickResponseDto(String clickDate, int count) {
        this.clickDate = clickDate;
        this.count = count;
    }
}
