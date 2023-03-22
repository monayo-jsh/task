package com.task.external.kakao;


import com.task.external.kakao.config.KaKaoConfig;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogRequest;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
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
public class KakaoApiCaller {

	 private final KaKaoConfig kaKaoConfig;

	 private final RestTemplate restTemplate;

	public KakaoApiCaller(KaKaoConfig kaKaoConfig, @Qualifier("kakaoRestTemplate") RestTemplate restTemplate) {
		this.kaKaoConfig = kaKaoConfig;
		this.restTemplate = restTemplate;
	}

	public KaKaoSearchBlogResponse getSearchBlog(KaKaoSearchBlogRequest searchBlogRequest) {
		if(ObjectUtils.isEmpty(searchBlogRequest.getQuery())) {
			throw new IllegalArgumentException("searchBlogRequest.getQuery() is empty");
		}

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kaKaoConfig.getSearchBlogEndpoint());
		uriBuilder.queryParam("query", searchBlogRequest.getQuery());

		if(!ObjectUtils.isEmpty(searchBlogRequest.getSort())) {
			uriBuilder.queryParam("sort", searchBlogRequest.getSort().name());
		}
		if(!ObjectUtils.isEmpty(searchBlogRequest.getPage())) {
			uriBuilder.queryParam("page", searchBlogRequest.getPage());
		}
		if(!ObjectUtils.isEmpty(searchBlogRequest.getSize())) {
			uriBuilder.queryParam("size", searchBlogRequest.getSize());
		}

		UriComponents uri = uriBuilder.build();

		HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", makeAuthorization());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(headers);

        ResponseEntity<KaKaoSearchBlogResponse> result = null;
		log.info("KakaoApiCaller.getSearchBlog :: {}", uri.toString());
        try {
        	result = restTemplate.exchange(uri.toString(), HttpMethod.GET, request, KaKaoSearchBlogResponse.class);
		}  catch (HttpStatusCodeException e) {
        	log.error("KakaoApiCaller.getSearchBlog Error: {}", e.getMessage());
        	return KaKaoSearchBlogResponse.fail();
	    }

        return result.getBody();
	}

	private String makeAuthorization() {
		return String.format("KakaoAK %s", kaKaoConfig.getApiKey());
	}

}
