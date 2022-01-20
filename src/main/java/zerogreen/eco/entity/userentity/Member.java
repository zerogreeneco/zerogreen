package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

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

    private String socialType;


    // 회원 가입용
    public Member(String username, String nickname, String phoneNumber, String password,
                  UserRole userRole,Boolean authState,VegetarianGrade vegetarianGrade) {
        super(username, phoneNumber, password, userRole,authState);
        this.vegetarianGrade = vegetarianGrade;
        this.nickname = nickname;
    }

    // 카카오 회원가입
    public Member(String username, String phoneNumber, String password, UserRole userRole, String nickname, String socialType) {
        super(username, phoneNumber, password, userRole);
        this.nickname = nickname;
        this.socialType = socialType;
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
