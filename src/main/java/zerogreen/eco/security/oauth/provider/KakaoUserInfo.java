package zerogreen.eco.security.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getUsername() {
        return String.valueOf(attributes.get("username"));
    }

    @Override
    public String getNickname() {
        return String.valueOf(attributes.get("nickname"));
    }

    @Override
    public String getPhoneNumber() {
        return String.valueOf(attributes.get("phoneNumber"));
    }

    @Override
    public String getVegetarian() {
        return String.valueOf(attributes.get("vegetarian"));
    }
}
