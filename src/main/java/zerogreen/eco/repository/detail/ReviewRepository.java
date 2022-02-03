package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.entity.userentity.StoreMember;

public interface ReviewRepository {
    //Page<StoreReviewDto> findByStoreReview (Pageable pageable);
    Page<MemberReviewDto> findByStore(Pageable pageable, StoreMember storeMember);

    }
