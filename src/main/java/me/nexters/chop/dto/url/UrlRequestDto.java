package me.nexters.chop.dto.url;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.nexters.chop.config.ValidUrl;

import javax.validation.constraints.NotNull;


/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
@ApiModel(value = "Url 요청 DTO")
public class UrlRequestDto {
    @NotNull
    @ValidUrl
    @ApiModelProperty(notes = "줄이려는 Url", required = true)
    private String originUrl;

    @Builder
    public UrlRequestDto(String originUrl) {
        this.originUrl = originUrl;
    }
}
