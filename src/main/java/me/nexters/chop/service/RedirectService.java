package me.nexters.chop.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import me.nexters.chop.domain.url.Url;
import me.nexters.chop.repository.url.RedirectRepository;

/**
 * @author junho.park
 */
@Slf4j
@Service
public class RedirectService {
	private final RedirectRepository redirectRepository;

	public RedirectService(RedirectRepository redirectRepository) {
		this.redirectRepository = redirectRepository;
	}

	public Url redirect(String shortenUrl) {
		return Optional.ofNullable(redirectRepository.findUrlByShortUrl(shortenUrl))
			.orElseThrow(() -> new EntityNotFoundException("nexters.me/" + shortenUrl + " 는 등록되지 않은 URL 입니다."));
	}
}
