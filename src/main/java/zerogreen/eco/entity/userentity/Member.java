package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BasicUser {

    @Enumerated(EnumType.STRING)
    private VegetarianGrade vegetarianGrade;

    // 회원 가입
    public Member(String username, String nickname, String phoneNumber, String password, UserRole userRole, VegetarianGrade vegetarianGrade) {
        super(username, nickname, phoneNumber, password, userRole);
        this.vegetarianGrade = vegetarianGrade;
    }

    // 수정

    public Member(String nickname, String phoneNumber, VegetarianGrade vegetarianGrade) {
        super(nickname, phoneNumber);
        this.vegetarianGrade = vegetarianGrade;
    }
}
