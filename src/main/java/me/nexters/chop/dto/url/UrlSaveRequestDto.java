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
    private String longUrl;
    private String shortUrl;

    @Builder
    public UrlSaveRequestDto(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public Url toEntity() {
        return Url.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();
    }
}
