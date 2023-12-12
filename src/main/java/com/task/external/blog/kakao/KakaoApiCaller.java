package com.task.external.blog.kakao;


import com.task.external.blog.SearchBlogApi;
import com.task.external.blog.domain.SearchBlogRequest;
import com.task.external.blog.domain.SearchBlogResponse;
import com.task.external.blog.kakao.config.KaKaoConfig;
import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogRequest;
import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
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

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class KakaoApiCaller extends SearchBlogApi {

	 private final KaKaoConfig kaKaoConfig;

	 private final RestTemplate restTemplate;

	public KakaoApiCaller(KaKaoConfig kaKaoConfig, @Qualifier("kakaoRestTemplate") RestTemplate restTemplate) {
		this.kaKaoConfig = kaKaoConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	protected SearchBlogResponse resolve(SearchBlogRequest request) {
		if(ObjectUtils.isEmpty(request.getQuery())) {
			throw new IllegalArgumentException("searchBlogRequest.getQuery() is empty");
		}

		KaKaoSearchBlogRequest searchBlogRequest = KaKaoSearchBlogRequest.of(request);

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

		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<KaKaoSearchBlogResponse> result;
		log.info("KakaoApiCaller.getSearchBlog :: {}", uri);
		try {
			result = restTemplate.exchange(uri.toString(), HttpMethod.GET, httpEntity, KaKaoSearchBlogResponse.class);

			return SearchBlogResponse.of(Objects.requireNonNull(result.getBody()));
		}  catch (HttpStatusCodeException e) {
			log.error("KakaoApiCaller.getSearchBlog Error: {}", e.getMessage());
			return SearchBlogResponse.fail();
		}
	}

	private String makeAuthorization() {
		return String.format("KakaoAK %s", kaKaoConfig.getApiKey());
	}
}
