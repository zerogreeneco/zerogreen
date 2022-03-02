package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class CommunityRequestDto {

    @NotBlank
    private String text;
    @NotNull
    private Category category;
    private Member writer;
    private boolean chatCheck;
    private List<MultipartFile> imageFiles;

    public CommunityRequestDto(String text, Category category, boolean chatCheck) {
        this.text = text;
        this.category = category;
        this.chatCheck = chatCheck;
    }
}
