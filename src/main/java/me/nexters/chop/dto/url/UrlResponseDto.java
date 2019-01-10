package me.nexters.chop.dto.url;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
public class UrlResponseDto {
    private String originUrl;
    private String shortUrl;

    @Builder
    public UrlResponseDto(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }
}
