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

    //상세페이지에 나타나는 가게별 리뷰 수
    @Query("select count(mr.id) from MemberReview mr " +
            "left outer join BasicUser bu on mr.basicUser.id = bu.id " +
            "where bu.userRole ='USER' and mr.storeMember.id =:sno")
    Long counting(@Param("sno") Long sno);

    //memberMyInfo에 나타나는 회원별 리뷰 수
   @Query("select count(mr.id) from MemberReview mr " +
           "where mr.basicUser =:basicUser ")
    Long countReviewByUser(@Param("basicUser") BasicUser basicUser);

   //memberMyInfo에 나타나는 회원별 리뷰남긴 가게 리스트
    @Query("select mr from MemberReview mr where mr.basicUser =:basicUser")
    List<MemberReview> getReviewByUser(@Param("basicUser") BasicUser basicUser);

/*
    @Query("select mr from MemberReview mr where mr.storeMember.id =sno")
    List<MemberReview> findByStore(@Param("sno")Long sno);
*/


    }