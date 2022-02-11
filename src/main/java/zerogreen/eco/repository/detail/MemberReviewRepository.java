package zerogreen.eco.repository.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import java.util.List;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long>, ReviewRepository {

/*
    @Query("select mr from MemberReview mr where mr.storeMember.id =sno")
    List<MemberReview> findByStore(@Param("sno")Long sno);
*/


    }