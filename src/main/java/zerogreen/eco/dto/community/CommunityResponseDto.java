package zerogreen.eco.dto.community;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommunityResponseDto {

    private Long id;
    private String text;
    private List<BoardImage> imageList;
    private LocalDateTime modifiedDate;
    private Category category;
    private int count;
    private Long like;

    private String nickname;

    public CommunityResponseDto(Long id, String text, List<BoardImage> imageList,
                                LocalDateTime modifiedDate, String nickname) {
        this.id = id;
        this.text = text;
        this.imageList = imageList;
        this.modifiedDate = modifiedDate;
        this.nickname = nickname;
    }

    public CommunityResponseDto(Long id, String text, String nickname, Category category, LocalDateTime modifiedDate,
                                int count, Long like) {
        this.id = id;
        this.text = text;
        this.nickname = nickname;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.count = count;
        this.like = like;
    }

    public CommunityResponseDto(Long id, String text, String nickname, Category category, LocalDateTime modifiedDate,
                                int count, Long like, List<BoardImage> imageList) {
        this.id = id;
        this.text = text;
        this.nickname = nickname;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.count = count;
        this.like = like;
        this.imageList = (imageList != null) ? imageList : new ArrayList<>();
    }
}
