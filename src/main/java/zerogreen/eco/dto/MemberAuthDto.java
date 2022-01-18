package zerogreen.eco.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthDto {

    private String nickname;
    private String authKey;

    public MemberAuthDto() {
    }

    public MemberAuthDto(String nickname, String authKey) {
        this.nickname = nickname;
        this.authKey = authKey;
    }
}
