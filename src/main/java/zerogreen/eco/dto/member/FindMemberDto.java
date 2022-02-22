package zerogreen.eco.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FindMemberDto {

//    @NotBlank(message = "비밀번호를 찾을 아이디를 입력해주세요.")
    @Email
    private String username;

//    @NotBlank(message = "계정과 연결된 연락처를 입력해주세요.")
    private String phoneNumber;

    public FindMemberDto() {}

    public FindMemberDto(String username) {
        this.username = username;
    }

    public FindMemberDto(String username, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
}
