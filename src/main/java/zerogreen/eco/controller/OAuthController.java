package zerogreen.eco.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.member.MemberJoinDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.oauth.KakaoProfile;
import zerogreen.eco.security.oauth.OAuthToken;
import zerogreen.eco.service.user.MemberService;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class OAuthController {

    @Value("${kakao.pwd}")
    private String kakaoPwd;

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    private final String KAKAO_CLIENT_ID = "ed6be877b0d7d2dddcc441eaae07a48c";
    private final String KAKAO_REDIRECT_URI = "http://localhost:8080/zerogreen/auth/kakao/callback";


    @GetMapping("auth/kakao/callback")
    public String kakaoCallback(String code, RedirectAttributes redirectAttributes, HttpSession session) { // Data를 리턴하는 컨트롤러 함수(@ResponseBody)
//
//        return code;
        // POST 방식으로 key=value 데이터를 카카오에 요청
        RestTemplate rt = new RestTemplate();

        // HttpBody 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("client_id", KAKAO_CLIENT_ID);
        param.add("redirect_uri", KAKAO_REDIRECT_URI);
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

        // JSON 변환
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("카카오 엑세스 토큰 ={}", oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpBody 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // key=value 형태의 데이터

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청하기 -> POST 방식으로 받고 Response를 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST, // 요청 방식
                kakaoProfileRequest,
                String.class // 응답받을 클래스
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String memberId = kakaoProfile.getKakao_account().getEmail();

        Member member = new Member(memberId, kakaoPwd, String.valueOf(UserRole.USER), null,
                kakaoProfile.getProperties().getNickname(), "KAKAO");

        Long joinedMember = memberService.findAuthMember(memberId);
        log.info("JOIN={}", joinedMember);
        if (joinedMember < 1) {
            log.info("자동 회원가입");
            Long saveMember = memberService.saveV2(member);
            redirectAttributes.addAttribute("memberId", saveMember);
            log.info("memberId={}", saveMember);
            return "redirect:/members/kakao/addData";
        }

        // 아이디와 패스워드로 시큐리티가 알아볼 수 있는 token 객체로 변경
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberId, kakaoPwd);
        log.info("KAKAO TOKEN={}", token);
        // AuthenticationManager 에 token을 넘기면 UserDetailsService가 받아서 처리
        Authentication authentication = authenticationManager.authenticate(token);
        log.info("TOKEN={}", token);
        // 실ㅈ SecurityContext에 authentication 정보 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("자동 로그인 완료");

        return "index";
    }

    @GetMapping("/members/kakao/addData")
    public String kakaoAddDateForm(@RequestParam("memberId")Long memberId, @ModelAttribute("member") MemberJoinDto joinDto, Model model) {
        model.addAttribute("vegan", VegetarianGrade.values());
        model.addAttribute("memberId", memberId);

        return "register/kakaoAddDataForm";
    }

    @PostMapping("/members/kakao/addData")
    @ResponseBody
    public String kakaoAdd(@Validated @ModelAttribute("member") MemberJoinDto joinDto, BindingResult bindingResult, Model model,
                           Long memberId,String phoneNumber,String vegetarianGrade) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("vegan", VegetarianGrade.values());
        }

        log.info("JOIN DTO={}",joinDto.getPhoneNumber() + "_" + phoneNumber);
        log.info("JOIN DTO={}",joinDto.getVegetarianGrade() + "_" + vegetarianGrade);
        log.info("<<<<<<MEMBERID={}", memberId);
        Member member = joinDto.toMember(joinDto);
        log.info("KAKAOMEMBER={}",member.getPhoneNumber());

        memberService.kakaoMemberUpdate(memberId, member);
        log.info(">>>>>>MEMBERID={}", memberId);

        return "redirect:/";
    }
}
