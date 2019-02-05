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
@ApiModel(value = "요청된 url에 대한 총 카운트 통계 DTO")
public class StatsTotalResponseDto {
    @ApiModelProperty(notes = "단축 url", required = true)
    private String shortUrl;

    @ApiModelProperty(notes = "url 총 클릭 횟수", required = true)
    private int totalCount;

    @Builder
    public StatsTotalResponseDto(String shortUrl, int totalCount) {
        this.shortUrl = shortUrl;
        this.totalCount = totalCount;
    }
}
