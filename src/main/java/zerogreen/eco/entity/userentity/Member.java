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
    private String nickname;


    // 회원 가입용
    public Member(String username, String nickname, String phoneNumber, String password,
                  UserRole userRole, String authKey,Boolean authState,VegetarianGrade vegetarianGrade) {
        super(username, phoneNumber, password, userRole,authKey,authState);
        this.vegetarianGrade = vegetarianGrade;
        this.nickname = nickname;
    }

    // 수정용
    public Member(String nickname, String phoneNumber, VegetarianGrade vegetarianGrade) {
        super(nickname, phoneNumber);
        this.vegetarianGrade = vegetarianGrade;
    }

    // 테스트용
    public Member(String username, String nickname, String phoneNumber, String password, UserRole userRole, VegetarianGrade vegetarianGrade) {
        super(username, phoneNumber, password, userRole);
        this.vegetarianGrade = vegetarianGrade;
        this.nickname = nickname;
    }
}
