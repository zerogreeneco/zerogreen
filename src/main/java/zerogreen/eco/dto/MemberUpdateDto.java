package zerogreen.eco.dto;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.VegetarianGrade;

@Getter
@Setter
public class MemberUpdateDto {

    private String nickname;
    private String phoneNumber;

    private VegetarianGrade vegetarianGrade;


}
