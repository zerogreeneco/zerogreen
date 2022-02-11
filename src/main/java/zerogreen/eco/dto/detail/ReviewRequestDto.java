package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

@Getter
@Setter
public class ReviewRequestDto {
    private String reviewText;

    private Long id;
    private String username;

    private String nickname;

    private StoreMember storeMember;
    private BasicUser basicUser;

    private List<MultipartFile> imageFiles;

    public ReviewRequestDto(String reviewText) {
        this.reviewText = reviewText;
    }

}
