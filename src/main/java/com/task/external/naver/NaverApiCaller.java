package com.task.external.naver;


import com.task.external.naver.config.NaverConfig;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogRequest;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogResponse;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class NaverApiCaller {

	 private final NaverConfig naverConfig;

	 private final RestTemplate restTemplate;

	public NaverApiCaller(NaverConfig naverConfig, @Qualifier("naverRestTemplate") RestTemplate restTemplate) {
		this.naverConfig = naverConfig;
		this.restTemplate = restTemplate;
	}

	public NaverSearchBlogResponse getSearchBlog(NaverSearchBlogRequest searchBlogRequest) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(naverConfig.getSearchBlogEndpoint());
		uriBuilder.queryParam("query", searchBlogRequest.getQuery());

		if(!ObjectUtils.isEmpty(searchBlogRequest.getSort())) {
			uriBuilder.queryParam("sort", searchBlogRequest.getSort().name());
		}
		if(!ObjectUtils.isEmpty(searchBlogRequest.getStart())) {
			uriBuilder.queryParam("start", searchBlogRequest.getStart());
		}
		if(!ObjectUtils.isEmpty(searchBlogRequest.getDisplay())) {
			uriBuilder.queryParam("display", searchBlogRequest.getDisplay());
		}

		UriComponents uri = uriBuilder.build();

		HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfig.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfig.getClientSecret());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(headers);

        ResponseEntity<NaverSearchBlogResponse> result = null;
		log.info("NaverApiCaller.getSearchBlog :: {}", uri.toString());
        try {
        	result = restTemplate.exchange(uri.toString(), HttpMethod.GET, request, NaverSearchBlogResponse.class);
		}  catch (HttpStatusCodeException e) {
        	log.error("NaverApiCaller.getSearchBlog Error: {}", e.getMessage());
        	return NaverSearchBlogResponse.fail();
	    }

        return result.getBody();
	}

}
