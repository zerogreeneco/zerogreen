package zerogreen.eco.security;

import lombok.Builder;
import lombok.Getter;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String registrationId;
    private String nameAttributeKey;
    private String username;
    private String nickname;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String registrationId, String nameAttributeKey, String username, String nickname) {
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.username = username;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(registrationId, "id", attributes);
    }

    private static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        profile.put("username", kakaoAccount.get("email"));
        profile.put("nickname", profile.get("nickname"));
        profile.put("id", attributes.get("id"));

        return OAuthAttributes.builder()
                .username(String.valueOf(profile.get("username")))
                .nickname(String.valueOf(profile.get("nickname")))
                .attributes(profile)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .userRole(UserRole.USER)
                .build();
    }

}
