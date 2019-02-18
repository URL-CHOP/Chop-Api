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
@ApiModel(value = "요청된 url에 대한 플랫폼 통계 DTO")
public class StatsPlatformResponseDto {
    @ApiModelProperty(notes = "모바일 클릭 횟수", required = true)
    private int mobile;

    @ApiModelProperty(notes = "브라우저 클릭 횟수", required = true)
    private int browser;

    @Builder
    public StatsPlatformResponseDto(int mobile, int browser) {
        this.mobile = mobile;
        this.browser = browser;
    }
}
