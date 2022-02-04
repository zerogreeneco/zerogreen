package zerogreen.eco.service.detail;

import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.userentity.BasicUser;

public interface LikesService {
    //좋다구요
    void addLikes(Long sno, BasicUser basicUser);
    //안좋다구요
    void removeLike(Long sno, Long mno);
    //나 얼만큼 좋아해요
    Long cntLikes(Long sno);
    //개별 라이크
    int cntMemberLike(Long sno, Long mno);
    //라이크 데이터
    //LikesDto liking(Long id, String username);


    }
