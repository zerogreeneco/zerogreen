package zerogreen.eco.dto.member;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberAuthDto {

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    public MemberAuthDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
