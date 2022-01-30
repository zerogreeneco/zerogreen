package zerogreen.eco.service.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.repository.detail.LikesRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    @Override
    public Long addLikes(Likes likes) {
        likesRepository.save(new Likes(likes.getBasicUser(),likes.getStoreMember()));
        return likes.getId();
    }


}
