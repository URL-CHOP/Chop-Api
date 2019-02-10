package me.nexters.chop.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * @author manki.kim
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PageProfileBusiness {
	private final RestTemplate restTemplate;

	public String getTitle(String url) {

		return null;
	}

}
