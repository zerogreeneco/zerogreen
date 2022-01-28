package zerogreen.eco.security.oauth;

import lombok.Getter;
import lombok.Setter;

// JSON으로 Parsing할 때 필요
@Getter
@Setter
public class OAuthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
