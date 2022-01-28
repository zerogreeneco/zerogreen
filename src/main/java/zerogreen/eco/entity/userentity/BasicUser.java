package zerogreen.eco.entity.userentity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "User_Type")
@DiscriminatorValue("ADMIN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BasicUser extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private boolean AuthState; // 이메일 인증 여부

    // 회원가입
    public BasicUser(String username, String phoneNumber,
                     String password, UserRole userRole, boolean authState) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        AuthState = authState;
    }

    // Kakao 회원가입
    public BasicUser(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    // 수정용
    public BasicUser(String username, String phoneNumber, String password, UserRole userRole) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
    }

    // 수정용
    public BasicUser(String nickname, String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /*
    * Setter
    * */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setAuthState(boolean authState) {
        AuthState = authState;
    }

    //memberReview
    public BasicUser(String username) {
        super();
    }

}
