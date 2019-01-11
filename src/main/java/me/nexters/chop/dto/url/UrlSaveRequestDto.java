package me.nexters.chop.dto.url;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import me.nexters.chop.domain.url.Url;

/**
 * @author junho.park
 */
@NoArgsConstructor
@Getter
public class UrlSaveRequestDto {
    private String originUrl;
    private String shortUrl;

    @Builder
    public UrlSaveRequestDto(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .shortUrl(shortUrl)
                .build();
    }
}
