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

import java.util.Optional;


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
        return likesRepository.save(new Likes(findUser, findStore))
                .getId();
    }

    //안좋아요 흥!
    @Override
    public String remove(Long id) {
        String key = "Delete";
        likesRepository.deleteById(id);
        return key;
    }

    //카운팅스타~ 밤하늘의 퍼어어얼
    @Override
    public Long cntLikes(LikesDto likesDto) {
        StoreMember findStore = storeMemberRepository.findById(likesDto.getId()).orElseThrow();
        return likesRepository.counting(findStore);
    }

    //라이크 데이터 뿌리기 ** 작업중 **
    @Override
    public LikesDto liking(Long id) {
        StoreMember findStore = storeMemberRepository.findById(id).orElseThrow();
        Likes likes = likesRepository.getLikesByStoreAndUser(findStore);
        return new LikesDto(likes.getId(),findStore, likes.getBasicUser());

        }
    }

