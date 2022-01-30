package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long> {

    @Query("select mr from MemberReview mr where mr.storeMember =:id")
    Page<MemberReviewDto> findByStore(Pageable pageable);
}