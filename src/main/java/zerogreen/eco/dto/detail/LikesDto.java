package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.mail.Store;
import java.time.LocalDateTime;

@Getter
@Setter
public class LikesDto {
    private Long lno;

    private Long id;
    private String username;

    private Long sno;
    private String storeName;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //이하 생성자 정리예정
    public LikesDto() {

    }

    public LikesDto(Long lno, StoreMember storeMember, BasicUser basicUser){
        this.lno = lno;
        this.storeMember = storeMember;
        this.basicUser = basicUser;
    }

}
