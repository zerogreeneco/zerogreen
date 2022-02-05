package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.repository.detail.LikesRepository;
import zerogreen.eco.repository.user.BasicUserRepository;
import zerogreen.eco.repository.user.StoreMemberRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final BasicUserRepository basicUserRepository;
    private final StoreMemberRepository storeMemberRepository;

    //좋아요
    @Override
    @Transactional
    public void addLikes(Long sno, BasicUser basicUser) {
        StoreMember findStore = storeMemberRepository.findById(sno).orElseThrow();
        likesRepository.save(new Likes(basicUser, findStore));
    }

    //안좋아요 흥!
    @Override
    public void removeLike(Long sno, Long mno) {
        log.info("bbbbbb9999: "+ sno);
        log.info("bbbbbb1010: "+ mno);
        likesRepository.deleteMemberLikes(sno, mno);
    }

    //카운팅스타~ 밤하늘의 퍼어어얼
    @Override
    public Long cntLikes(Long sno) {
        StoreMember findStore = storeMemberRepository.findById(sno).orElseThrow();
        return likesRepository.counting(findStore);
    }

    //개별 라이크 카운팅
    @Override
    public Long cntMemberLike(Long sno, Long mno) {
        log.info("bbbbbb6666: "+ sno);
        log.info("bbbbbb7777: "+ mno);
        return likesRepository.cntMemberLike(sno, mno);
    }

}

