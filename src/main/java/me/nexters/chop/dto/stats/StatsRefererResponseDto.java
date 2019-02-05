package me.nexters.chop.dto.stats;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
@ApiModel(value = "요청된 url에 대한 레퍼러 통계 DTO")
public class StatsRefererResponseDto {
    @ApiModelProperty(notes = "단축 url", required = true)
    private String shortUrl;

    @ApiModelProperty(notes = "레퍼러", required = true)
    private List<String> referer;

    @ApiModelProperty(notes = "레퍼러 유입 횟수", required = true)
    private List<Integer> count;

    @Builder
    public StatsRefererResponseDto(String shortUrl, List<String> referer, List<Integer> count) {
        this.shortUrl = shortUrl;
        this.referer = referer;
        this.count = count;
    }
}
