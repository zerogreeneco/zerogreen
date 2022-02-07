package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.StoreMember;

public interface ReviewRepository extends JpaRepository<MemberReview, Long>, ReviewRepositoryCustom {
    Page<MemberReviewDto> findByStore(Pageable pageable, StoreMember storeMember);

    }
