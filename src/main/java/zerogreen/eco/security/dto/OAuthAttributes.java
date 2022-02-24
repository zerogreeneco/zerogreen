package zerogreen.eco.security.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String username;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String username) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.username = username;
    }

    // OAuth2User에서 반환되는 사용자 정보는 Map 형태. of()에서 값을 받으면 registrationId로 어떤 서비스인지 구분한 뒤 of~를 통해서 값을 변환
    public static OAuthAttributes of(String registrationId, String usernameAttributesName, Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(usernameAttributesName, attributes);
    }

    // of()에서 넘어온 값을 변환
    private static OAuthAttributes ofKakao(String usernameAttributesName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("id");

        return OAuthAttributes.builder()
                .username((String) response.get("email"))
                .nickname(String.valueOf(response.get("nickname")))
                .attributes(response)
                .nameAttributeKey(usernameAttributesName)
                .build();
    }

    private static OAuthAttributes ofNaver(String usernameAttributesName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .username((String) response.get("email"))
                .nickname(String.valueOf(response.get("nickname")))
                .attributes(response)
                .nameAttributeKey(usernameAttributesName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String usernameAttributesName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname(String.valueOf(attributes.get("name")))
                .username(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributesName)
                .build();
    }

    // Member 엔티티를 생성(처음 가입할 때 한번만 생성)
    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .userRole(UserRole.USER)
                .phoneNumber("")
                .authState(false)
                .nickname(nickname)
                .vegetarianGrade(VegetarianGrade.LACTO)
                .build();
    }
}
