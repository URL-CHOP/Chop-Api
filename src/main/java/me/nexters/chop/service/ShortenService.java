package me.nexters.chop.service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.dto.url.UrlSaveRequestDto;

/**
 * @author junho.park
 */
public interface ShortenService {
    String base62Encode(int inputNumber);
    Url saveUrl(UrlSaveRequestDto urlSaveRequestDto);
    int findMaxIdFromDatabase();
}
