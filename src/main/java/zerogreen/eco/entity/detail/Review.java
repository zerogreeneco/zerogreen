package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.dto.detail.ReviewDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends BasicUser {
    private Long rno;

    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    //DtoToEntity..?
    public Review(String username, String phoneNumber, String password, UserRole userRole,
                  Long rno, String reviewText, String nickname, String storeName) {
        super(username, phoneNumber, password, userRole);
        this.rno = rno;
        this.reviewText = reviewText;
        this.member = new Member(nickname);
        this.storeMember = new StoreMember(storeName);
    }

}
