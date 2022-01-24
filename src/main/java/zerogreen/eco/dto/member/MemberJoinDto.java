package zerogreen.eco.dto.member;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberJoinDto {

    @NotBlank
    @Email // @ 없으면 에러 발생 마지막 .com 등이 빠져도 로직 진행 Ok
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    @NotBlank
//    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String password;

    private VegetarianGrade vegetarianGrade;

    public Member toMember(MemberJoinDto memberJoinDto) {
        return new Member(memberJoinDto.username, memberJoinDto.nickname,
                memberJoinDto.phoneNumber, memberJoinDto.password, UserRole.USER, false, memberJoinDto.vegetarianGrade);
    }


}
