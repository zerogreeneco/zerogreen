package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.detail.Likes;

import java.util.Optional;

public interface LikesService {
    //좋다구요
    Long addLikes(LikesDto likesDto);
    //안좋다구요
    String remove(Long id);
    //나 얼만큼 좋아해요
    Long cntLikes(LikesDto likesDto);
    //라이크 데이터
    LikesDto liking(Long id);


    }
