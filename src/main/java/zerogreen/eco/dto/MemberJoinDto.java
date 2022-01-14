package zerogreen.eco.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberJoinDto {

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    private VegetarianGrade vegetarianGrade;

    public Member toMember(MemberJoinDto memberJoinDto) {
        return new Member(memberJoinDto.username, memberJoinDto.nickname,
                memberJoinDto.phoneNumber, memberJoinDto.password, UserRole.USER, memberJoinDto.vegetarianGrade);
    }
}
