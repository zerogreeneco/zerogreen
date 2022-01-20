package zerogreen.eco.oauth.security;

import lombok.Getter;
import lombok.Setter;

// KAKAO에서 넘어오는 key값과 매핑하기 위해서 카멜 표기법 X
@Getter
@Setter
public class KakaoProfile {

    private Integer id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    @Setter
    public class Properties {private String nickname;}

    @Getter
    @Setter
    public class KakaoAccount {
        private Boolean profile_nickname_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        @Getter
        @Setter
        public class Profile { private String nickname;}

    }
}






