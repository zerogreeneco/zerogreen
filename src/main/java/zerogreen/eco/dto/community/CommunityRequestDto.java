package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.userentity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CommunityRequestDto {

    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    private Category category;
    private Member writer;
    private List<MultipartFile> imageFiles;

}