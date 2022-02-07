package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.userentity.StoreMember;

public interface ReviewRepositoryCustom {
    Page<MemberReviewDto> findByStore(Pageable pageable, StoreMember storeMember);
}
