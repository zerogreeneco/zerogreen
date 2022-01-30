package zerogreen.eco.security.oauth.provider;

public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();

    String getUsername();

    String getNickname();

    String getPhoneNumber();

    String getVegetarian();
}
