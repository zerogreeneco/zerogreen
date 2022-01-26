package zerogreen.eco.dto.detail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import zerogreen.eco.entity.detail.Review;
import zerogreen.eco.entity.userentity.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {
    private Long rno;
    private String reviewText;

    private String username;
    private String phoneNumber;
    private String password;
    private UserRole userRole;

    private String nickname;

    private String storeName;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //entitytodto..?
    public Review reviewDto(ReviewDto reviewDto) {
        return new Review(reviewDto.getUsername(),reviewDto.getPhoneNumber(),reviewDto.getPassword(),reviewDto.getUserRole(),
                reviewDto.getRno(),reviewDto.getReviewText(),reviewDto.getNickname(),reviewDto.getStoreName());
    }

}
