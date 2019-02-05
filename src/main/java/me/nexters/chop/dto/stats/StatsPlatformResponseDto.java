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
    @ApiModelProperty(notes = "단축 url", required = true)
    private String shortUrl;

    @ApiModelProperty(notes = "모바일 클릭 횟수", required = true)
    private int mobile;

    @ApiModelProperty(notes = "브라우저 클릭 횟수", required = true)
    private int browser;

    @Builder
    public StatsPlatformResponseDto(String shortUrl, int mobile, int browser) {
        this.shortUrl = shortUrl;
        this.mobile = mobile;
        this.browser = browser;
    }
}
