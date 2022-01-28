package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long rno;

    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private BasicUser basicUser;


    //Entity db랑 연결할 때
    public MemberReview(String reviewText, StoreMember storeMember, BasicUser basicUser) {
        this.reviewText = reviewText;
        this.storeMember = storeMember;
        this.basicUser = basicUser;

    }
/*
    public MemberReview(String username, String reviewText, String storeName) {
        this.basicUser = new BasicUser(username);
        this.reviewText = reviewText;
        this.storeMember = new StoreMember(storeName);
    }
*/

}
