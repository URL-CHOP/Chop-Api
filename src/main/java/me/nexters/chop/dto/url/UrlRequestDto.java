package me.nexters.chop.dto.url;

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
public class UrlRequestDto {
    @NotNull
    @ValidUrl
    private String originUrl;

    @Builder
    public UrlRequestDto(String originUrl) {
        this.originUrl = originUrl;
    }
}
