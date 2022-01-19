package zerogreen.eco.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthDto {

    private String authKey;

    public MemberAuthDto() {
    }

    public MemberAuthDto(String authKey) {
        this.authKey = authKey;
    }
}
