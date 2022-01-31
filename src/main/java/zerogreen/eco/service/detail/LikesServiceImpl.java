package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerogreen.eco.dto.detail.LikesDto;
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
    public Long addLikes(LikesDto likesDto) {
        BasicUser findUser = basicUserRepository.findByUsername(likesDto.getUsername()).orElseThrow();
        StoreMember findStore = storeMemberRepository.findById(likesDto.getId()).orElseThrow();
        log.info("vvvvvvvv666666: " +findUser.getId());
        log.info("vvvvvvvv777777: " +findStore.getId());
        return likesRepository.save(new Likes(findUser, findStore))
                .getId();
    }

    //안좋아요 흥!
    @Override
    public void remove(LikesDto likesDto) {
        log.info("qqqqq11111: "+ likesDto.getId());
        log.info("qqqqq22222: "+ likesDto.getLno());
        likesRepository.deleteById(likesDto.getLno());
    }

    //카운팅스타~ 밤하늘의 퍼어어얼
    @Override
    public Long cntLikes(LikesDto likesDto) {
        StoreMember findStore = storeMemberRepository.findById(likesDto.getId()).orElseThrow();
        return likesRepository.counting(findStore);
    }

}
