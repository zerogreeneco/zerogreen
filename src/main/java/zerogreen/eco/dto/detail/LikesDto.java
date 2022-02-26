package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;

import java.time.LocalDateTime;

@Getter
@Setter
public class LikesDto {
    private Long lno;
    private Long count;

    private Long sno;
    private StoreMember storeMember;

    private Long id;
    private BasicUser basicUser;

    private LocalDateTime createdDate;

    private String thumbnailName;


    //라이크 리스팅 (memberMyInfo)
    public LikesDto(Long lno, StoreMember storeMember, BasicUser basicUser, String thumbnailName){
        this.lno = lno;
        this.storeMember = storeMember;
        this.basicUser = basicUser;
        this.thumbnailName = thumbnailName;
    }

}
