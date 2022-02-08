package zerogreen.eco.service.detail;

import zerogreen.eco.entity.userentity.BasicUser;

public interface LikesService {
    //좋다구요
    void addLikes(Long sno, BasicUser basicUser);
    //안좋다구요
    void removeLike(Long sno, Long mno);
    //나 얼만큼 좋아해요
    Long cntLikes(Long sno);
    //회원의 가게별 라이크 카운팅
    Long cntMemberLike(Long sno, Long mno);
    //멤버별 전체 라이크 수 카운팅 (memberMyInfo)
    Long countLikesByUser(Long id);

    }
