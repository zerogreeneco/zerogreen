package zerogreen.eco.security.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.MemberRepository;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.security.oauth.provider.KakaoUserInfo;
import zerogreen.eco.security.oauth.provider.OAuth2UserInfo;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    @Lazy // 임시 방편 -> 순환참조 방지
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("getClientRegistration={}", userRequest.getClientRegistration());
        log.info("getAccessToken={}", userRequest.getAccessToken());

        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("Kakao")) {
            log.info("카카오 로그인 요청 !!!!!!!");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            log.info("카카오만 넘어와라");
        }

        String username = oAuth2UserInfo.getUsername();
        String providerId = oAuth2UserInfo.getProviderId();
        String password = passwordEncoder.encode(oAuth2UserInfo.getProvider() + "_" + providerId);
        String nickname = oAuth2UserInfo.getNickname();

        Member findMember = memberRepository.findByUsername(username).orElseThrow();

        if (findMember == null) {
            log.info("최초 로그인");
            findMember = Member.builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .userRole(UserRole.USER)
                    .build();
            memberRepository.save(findMember);
        } else {
            log.info("이미 소셜 로그인을 했습니다.");
        }
        return new PrincipalDetails(findMember, oAuth2User.getAttributes());
    }
}
