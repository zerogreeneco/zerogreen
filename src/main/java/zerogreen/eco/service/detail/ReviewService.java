package zerogreen.eco.service.detail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.StoreReview;

public interface ReviewService {
    //멤버리뷰 DB저장
    Long saveReview(MemberReviewDto memberReviewDto);
    Long saveTest(MemberReview memberReview);
    //가게별 멤버 리뷰 카운팅
    Long cntMemberReview(MemberReviewDto memberReviewDto);
    //멤버리뷰 리스팅
    Page<MemberReview> getMemberReviewList(Pageable pageable, Long id);
    //멤버리뷰 삭제
    void remove(Long id);
    //멤버리뷰 수정
    void modifyReview(MemberReviewDto memberReviewDto);
    //스토어멤버 리뷰 DB
    Long saveStoreReview(StoreReviewDto storeReviewDto);
    Long ssrT(StoreReview storeReview);
    //스토어멤버 리뷰 수정
    void modifyStoreReview(StoreReviewDto storeReviewDto);
    //스토어멤버 리뷰 삭제
    void deleteStoreReview(Long id);
    //스토어멤버 리뷰 리스팅
    Page<StoreReview> getStoreReviewList(Pageable pageable, Long id);
    //Page<StoreReviewDto> getStoreReviewList(Pageable pageable);



    }
