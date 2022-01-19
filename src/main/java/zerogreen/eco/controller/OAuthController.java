package zerogreen.eco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/")
public class OAuthController {

    private final String KAKAO_CLIENT_ID = "ed6be877b0d7d2dddcc441eaae07a48c";
    private final String KAKAO_REDIRECT_URI = "http://localhost:8080/zerogreen/auth/kakao/callback";

    @GetMapping("auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) { // Data를 리턴하는 컨트롤러 함수(@ResponseBody)


        // POST 방식으로 key=value 데이터를 카카오에 요청
        RestTemplate rt = new RestTemplate();

        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("client_id", "ed6be877b0d7d2dddcc441eaae07a48c");
        param.add("redirect_uri", "http://localhost:8080/zerogreen/auth/kakao/callback");
        param.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(param, headers);

        // Http 요청하기 -> POST 방식으로 받고 Response를 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, // 요청 방식
                kakaoTokenRequest,
                String.class // 응답받을 클래스
        );

        return "카카오 토근 요청 완료" + response;
    }
}
