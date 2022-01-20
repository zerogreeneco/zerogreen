package zerogreen.eco.dto.member;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberUpdateDto {

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    private VegetarianGrade vegetarianGrade;

    public MemberUpdateDto(Member member) {
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
        this.vegetarianGrade = member.getVegetarianGrade();
    }
}
