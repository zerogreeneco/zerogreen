package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends User {

    @Enumerated(EnumType.STRING)
    private VegetarianGrade vegetarianGrade;

    public Member(String username, String nickname, String phoneNumber, String password, UserRole userRole, VegetarianGrade vegetarianGrade) {
        super(username, nickname, phoneNumber, password, userRole);
        this.vegetarianGrade = vegetarianGrade;
    }
}
