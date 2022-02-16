package zerogreen.eco.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.VegetarianGrade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityResponseDto {

    private Long id;
    private String text;
    private List<BoardImage> imageList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asis/Seoul")
    private LocalDateTime modifiedDate;
    private Category category;
    private int count;
    private Long like;

    private String nickname;
    private String imageName;
    private VegetarianGrade vegetarianGrade;

    private Long replyCount;

    private List<ImageFileDto> files = new ArrayList<>();

    public CommunityResponseDto(Long id, String text, List<BoardImage> imageList,
                                LocalDateTime modifiedDate, String nickname) {
        this.id = id;
        this.text = text;
        this.imageList = imageList;
        this.modifiedDate = modifiedDate;
        this.nickname = nickname;
    }

    public CommunityResponseDto(Long id, String text, String nickname, VegetarianGrade vegetarianGrade, Category category, LocalDateTime modifiedDate,
                                int count, Long like, Long replyCount, String imageName) {
        this.id = id;
        this.text = text;
        this.nickname = nickname;
        this.vegetarianGrade = vegetarianGrade;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.count = count;
        this.like = like;
        this.replyCount = replyCount;
        this.imageName = imageName;
    }

    public CommunityResponseDto(Long id, String text, String nickname, Category category, LocalDateTime modifiedDate,
                                int count, Long like, Long replyCount, List<BoardImage> imageList) {
        this.id = id;
        this.text = text;
        this.nickname = nickname;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.count = count;
        this.like = like;
        this.replyCount = replyCount;
        this.imageList = (imageList != null) ? imageList : new ArrayList<>();
    }
}
