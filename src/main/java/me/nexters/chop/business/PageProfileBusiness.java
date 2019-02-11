package me.nexters.chop.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author manki.kim
 */

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PageProfileBusiness {

	private final RestTemplate restTemplate;
	private static final int TITLE_GET = 2;
	private static final Pattern TITLE_REGEX = Pattern.compile("(<title>)([A-Z0-9a-zㄱ-ㅎㅏ-ㅣ가-힣:+&@#/%?=~_|!:,.;-]+)");

	public String getTitle(String url) {

		Matcher matcher = TITLE_REGEX.matcher(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()),String.class).getBody().replaceAll("\\s",""));
		if (matcher.find()) {
			 return Stream.of(matcher.group(TITLE_GET))
					.map(title -> decoding(title))
					 .findFirst()
					 .get();
		}

		return null;
	}


	private String decoding(String s) {
		try {
			return URLDecoder.decode(s,"UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

}
