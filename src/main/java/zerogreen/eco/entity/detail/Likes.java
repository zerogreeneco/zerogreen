package zerogreen.eco.entity.detail;

import lombok.*;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="likes_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeMember")
    private StoreMember storeMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private BasicUser basicUser;


    //이하 생성자 정리예정
    @Builder
    public Likes(Long id, BasicUser basicUser, StoreMember storeMember) {
        this.id = id;
        this.basicUser = basicUser;
        this.storeMember = storeMember;
    }

    public Likes(BasicUser basicUser) {
        this.basicUser = basicUser;
    }

    public Likes(BasicUser basicUser, StoreMember storeMember) {
        this.basicUser = basicUser;
        this.storeMember = storeMember;
    }
}
