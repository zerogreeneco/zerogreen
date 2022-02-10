package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;

@Getter
@Setter
public class ReviewDto {
    private String reviewText;

    private Long id;
    private String username;

    private String nickname;

    private StoreMember storeMember;
    private BasicUser basicUser;

    public ReviewDto(String reviewText, StoreMember storeMember) {
        this.reviewText = reviewText;
    }

}
