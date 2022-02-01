package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.List;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long> {

    @Query("select mr from MemberReview mr where mr.storeMember =:storeMember")
    //List<MemberReview> findByStore(StoreMember storeMember);
    Page<MemberReviewDto> findByStore(Pageable pageable);

    @Query("select count(mr.id) from MemberReview mr " +
            "left outer join BasicUser bu on mr.basicUser = bu.id " +
            "where bu.userRole ='USER' and mr.storeMember =:storeMember")
    Long counting(StoreMember storeMember);

}