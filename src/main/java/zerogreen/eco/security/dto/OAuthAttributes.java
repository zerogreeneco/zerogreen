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

    public static OAuthAttributes of(String registrationId, String usernameAttributesName, Map<String, Object> attributes) {

        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }


        return ofGoogle(usernameAttributesName, attributes);
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
