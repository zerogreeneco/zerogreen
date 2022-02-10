package zerogreen.eco.service.detail;

import zerogreen.eco.entity.userentity.BasicUser;

public interface DetailReviewService {
    //save reviews
    void saveReview(String reviewText, Long sno, BasicUser basicUser);
    //save comments
    void saveNestedReview(String reviewText, Long sno, BasicUser basicUser, Long rno);

    }
