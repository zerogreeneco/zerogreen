package zerogreen.eco.dto.member;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberUpdateDto {

    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String phoneNumber;

    private VegetarianGrade vegetarianGrade;

    public MemberUpdateDto() {}

    public MemberUpdateDto(String username, String nickname, String phoneNumber, VegetarianGrade vegetarianGrade) {
        this.username = username;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.vegetarianGrade = vegetarianGrade;
    }


}
