package zerogreen.eco.entity.detail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="storeReview_id")
    private Long srno;

    private String storeReviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private BasicUser basicUser;

}
