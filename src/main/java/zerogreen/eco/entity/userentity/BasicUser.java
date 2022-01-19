package zerogreen.eco.entity.userentity;

import lombok.*;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "User_Type")
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
    private String authKey; // 회원 가입 인증 번호
    private boolean AuthState; // 이메일 인증 여부

    public BasicUser(String username, String phoneNumber,
                     String password, UserRole userRole, String authKey, boolean authState) {
        this.username = username;

        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        this.authKey = authKey;
        AuthState = authState;
    }

    public BasicUser(String username, String phoneNumber, String password, UserRole userRole, boolean authState) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
        AuthState = authState;
    }

    public BasicUser(String username, String phoneNumber, String password, UserRole userRole) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
    }

    public BasicUser(String nickname, String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
