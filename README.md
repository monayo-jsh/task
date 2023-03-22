# task

1. 블로그 검색 API
	```
	GET /blog/search
	```
	> * Request
	> 
	>       keyword (String) : 검색어 (필수)
	>       sort (String) : accuracy(정확도순)/recency(최신순) (선택) * default : accuracy
	>       page (Integer) : 페이지 번호 (1~50) (선택) * default : 1
	>       size (Integer) : 페이지에 보여질 수 (1~50) (선택) * default : 10
	>   
	> * Resposne
	> 
	>       code (String) : 응답 코드 (필수)
	>       message (String) : 응답 메시지 (필수)
	>       result (Object) : 응답 객체 (필수)
	>         total_count (Integer) : 전체 개수 (필수)
	>         blogs (Array) : 블로그 목록 (선택)
	>           blog_name (String) : 블로그의 이름 (필수)
	>           title (String) : 블로그 글 제목 (필수)
	>           summary_contents (String) : 블로그 글 요약 (필수)
	>           blog_url (String) : 블로그 url (필수)
	>           thumbnail (String) : 블로그 썸네일 (선택)
	>           created_date (DateTime) : 블로그 작성시간 (필수), * ISO 8601 : YYYY-MM-DDTHH:mm:ss


2. 인기검색어 목록 API
	```
	GET /keyword/popular
	```
	> * Response
	> 
	>       code (String) : 응답 코드 (필수)
	>       message (String) : 응답 메시지 (필수)
	>       result (Object) : 응답 객체 (필수)
	>         keywords (Array) : 인기 검색어 목록 (선택)
	>           keyword (String) : 검색어 명 (필수)
	>           search_count (Integer) : 검색된 횟수 (필수)


3. 블로그 검색 서비스 페이지
	```
	http://localhost:8080/blog
	```
4. 인기검색어 목록 서비스 페이지
	```
	http://localhost:8080/keyword
	```

* 빌드
```
gradle clean build
```
* 서비스 실행
```
java -jar -DKAKAO_API_KEY={KAKAO_REST_API_KEY} -DNAVER_CLIENT_ID={NAVER_CLIENT_ID} -DNAVER_CLIENT_SECRET={NAVER_CLIENT_SECRET} task.jar
```

* 라이브러리
	* 서버
		* h2 (데이터베이스)
		* lombok (생성자, getter, setter 소스 자동 생성)
	* UI
		* 기능 구현
			* ajax 및 script : [jQeury 3.6.4](https://jquery.com/download/)
			* 페이징 : [jQuery pagination plugin v1.4.2](http://josecebe.github.io/twbs-pagination/)
		* css
			* 테이블 : [Bootstrap v4.1.3](https://getbootstrap.com/)
			* 페이징 : [pagination v2.1.4](https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.css)
