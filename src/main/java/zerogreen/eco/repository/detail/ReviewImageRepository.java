package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zerogreen.eco.dto.detail.ReviewImageDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.ReviewImage;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    @Query("select img from ReviewImage img where img.storeMember.id =:sno")
    List<ReviewImage> findByStore(@Param("sno") Long sno);

    @Query("select img from ReviewImage img where img.memberReview.id =:rno")
    List<ReviewImage> findByReview(@Param("rno") Long rno);



}
