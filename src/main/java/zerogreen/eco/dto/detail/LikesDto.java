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

    private Long count;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;



    public LikesDto() {
    }

    //라이크 리스팅 (memberMyInfo)
    public LikesDto(Likes likes){
        this.lno = likes.getId();
        this.storeMember = likes.getStoreMember();
        this.basicUser = likes.getBasicUser();
    }



}
