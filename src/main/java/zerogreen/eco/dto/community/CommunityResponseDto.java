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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asis/Seoul")
    private LocalDateTime modifiedDate;
    private Category category;
    private int count;
    private Long like;
    private String nickname;
    private String username;
    private Long memberId;
    private VegetarianGrade vegetarianGrade;
    private String thumbnail;
    private Long replyCount;
    private boolean isChat;

    public CommunityResponseDto(Long id, String text, String nickname, String username,VegetarianGrade vegetarianGrade,
                                Long memberId, Category category, LocalDateTime modifiedDate, boolean isChat,
                                int count, Long like, Long replyCount, String thumbnail) {
        this.id = id;
        this.text = text;
        this.nickname = nickname;
        this.username = username;
        this.vegetarianGrade = vegetarianGrade;
        this.memberId = memberId;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.isChat = isChat;
        this.count = count;
        this.like = like;
        this.replyCount = replyCount;
        this.thumbnail = thumbnail;
    }

}
