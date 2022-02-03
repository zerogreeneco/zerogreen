package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.StoreReview;
import zerogreen.eco.entity.userentity.StoreMember;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {
    @Query("select sr from StoreReview sr where sr.storeMember =:storeMember")
    Page<StoreReview> findByStoreReview (Pageable pageable, StoreMember storeMember);

    @Query("select sr from StoreReview sr where sr.memberReview =:memberReview")
    StoreReview getStoreAndRno(MemberReview memberReview);

    }
