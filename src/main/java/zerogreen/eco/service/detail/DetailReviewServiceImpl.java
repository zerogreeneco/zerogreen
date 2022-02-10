package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.community.BoardReply;
import zerogreen.eco.entity.community.CommunityBoard;
import zerogreen.eco.entity.detail.DetailReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.detail.DetailReviewRepository;
import zerogreen.eco.repository.detail.MemberReviewRepository;
import zerogreen.eco.repository.detail.ReviewImageRepository;
import zerogreen.eco.repository.detail.StoreReviewRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailReviewServiceImpl implements DetailReviewService {
    private final MemberReviewRepository memberReviewRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final BasicUserRepository basicUserRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final DetailReviewRepository detailReviewRepository;

    //save reviews
    @Override
    @Transactional
    public void saveReview(String reviewText, Long sno, BasicUser basicUser) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        detailReviewRepository.save(new DetailReview(reviewText, basicUser, storeMember)); // 생성자 없음 왜요,,?
    }

    //save comments
    @Override
    @Transactional
    public void saveNestedReview(String reviewText, Long sno, BasicUser basicUser, Long rno) {
        StoreMember storeMember = storeMemberRepository.findById(sno).orElseThrow();
        // 컨트롤러에서 넘어 온 부모 댓글의 PK로 해당 부모 댓글을 검색
        DetailReview parentReview = detailReviewRepository.findById(rno).orElseThrow();
        // 새로운 자식 댓글을 생성하고 저장
        DetailReview childReview = new DetailReview(reviewText, basicUser, storeMember);
        detailReviewRepository.save(childReview);
        // 부모 댓글에 자식 댓글을 리스트로 저장 (양방향 매핑)
        parentReview.addNestedReview(childReview);
    }

}
