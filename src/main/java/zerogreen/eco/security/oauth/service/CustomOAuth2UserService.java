package zerogreen.eco.security.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.repository.user.MemberRepository;
import zerogreen.eco.security.dto.OAuthAttributes;
import zerogreen.eco.security.dto.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("member", new SessionUser(member));

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(member.getUserRole().getKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByUsername(attributes.getUsername())
                .map(entity -> entity.update(attributes.getNickname()))
                .orElse(attributes.toEntity());
        return memberRepository.save(member);
    }

    //    @Autowired
//    @Lazy // 임시 방편 -> 순환참조 방지
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        log.info("getClientRegistration={}", userRequest.getClientRegistration());
//        log.info("getAccessToken={}", userRequest.getAccessToken());
//
//        OAuth2UserInfo oAuth2UserInfo = null;
//
//        if (userRequest.getClientRegistration().getRegistrationId().equals("Kakao")) {
//            log.info("카카오 로그인 요청 !!!!!!!");
//            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
//        } else {
//            log.info("카카오만 넘어와라");
//        }
//
//        String username = oAuth2UserInfo.getUsername();
//        String providerId = oAuth2UserInfo.getProviderId();
//        String password = passwordEncoder.encode(oAuth2UserInfo.getProvider() + "_" + providerId);
//        String nickname = oAuth2UserInfo.getNickname();
//
//        Member findMember = memberRepository.findByUsername(username).orElseThrow();
//
//        if (findMember == null) {
//            log.info("최초 로그인");
//            findMember = Member.builder()
//                    .username(username)
//                    .password(password)
//                    .nickname(nickname)
//                    .userRole(UserRole.USER)
//                    .build();
//            memberRepository.save(findMember);
//        } else {
//            log.info("이미 소셜 로그인을 했습니다.");
//        }
//        return new PrincipalDetails(findMember, oAuth2User.getAttributes());
//    }
}
