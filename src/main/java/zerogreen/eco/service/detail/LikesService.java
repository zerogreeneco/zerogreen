package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.detail.Likes;

public interface LikesService {
    //좋다구요
    Long addLikes(LikesDto likesDto);
    //안좋다구요
    void remove(LikesDto likesDto);
    //나 얼만큼 좋아해요
    Long cntLikes(LikesDto likesDto);


    }
