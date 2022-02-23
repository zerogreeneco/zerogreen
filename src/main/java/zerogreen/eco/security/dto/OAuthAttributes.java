package zerogreen.eco.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String username;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String username) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.username = username;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributesName, Map<String, Object> attributes) {
        return ofGoogle(usernameAttributesName, attributes);
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
                .password("1")
                .userRole(UserRole.USER)
                .phoneNumber("00000000000")
                .authState(false)
                .nickname(nickname)
                .vegetarianGrade(VegetarianGrade.LACTO)
                .build();
    }
}
