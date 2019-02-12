package me.nexters.chop.dto.url;

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
@ApiModel(value = "Url 응답 DTO")
public class UrlResponseDto {
    @ApiModelProperty(notes = "원본 Url", required = true)
    private String originUrl;

    @ApiModelProperty(notes = "줄여진 Url", required = true)
    private String shortUrl;

    @ApiModelProperty(notes = "html 태그 title 가져오기", required = true)
    private String title;

    @Builder
    public UrlResponseDto(String originUrl, String shortUrl, String title) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.title = title;
    }
}
