package zerogreen.eco.dto;

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

    @NotBlank
    private String password;


}
