package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.LikesService;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class LikesController {

    private final LikesService likesService;


    @PostMapping("/detailLikes/{sno}")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> detailLikes(@PathVariable("sno") Long sno, LikesDto likesDto,
                                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Map<String, Integer> resultMap = new HashMap<>();
        log.info("LIKE CONTROL OK");

        // 해당 회원이 좋아요를 누른 적이 있는지 확인 -> 있으면 1, 없으면 0
        int cntMemberLike = likesService.cntMemberLike(sno, principalDetails.getId());
        log.info("bbbbcntMemberLike "+cntMemberLike);
        log.info("bbbbcntSno "+sno);

        // JSON 형태로 View에 데이터를 전달하기 위해서 key:value로 변경
        resultMap.put("memberCnt", cntMemberLike);


        // 결과에 따라서 insert / delete 쿼리 분기
        if (cntMemberLike <= 0) {
            // insert
            likesService.addLikes(sno, principalDetails.getBasicUser());
            log.info("bbbbb1111: " + sno);
            log.info("bbbbb2222: " + principalDetails.getBasicUser());
            // insert 후에 DB에서 해당 게시물의 전체 좋아요 수 카운트 후 json으로 변환
            //resultMap.put("totalCount", likesService.countLikeByBoard(boardId));
        } else if (cntMemberLike > 0) {
            // delete
            likesService.removeLike(sno, principalDetails.getId());
            log.info("bbbbb3333: " + sno);
            log.info("bbbbb4444: " + principalDetails.getBasicUser());

            // delete 후에 DB에서 해당 게시물의 전체 좋아요 수 카운트 후 json으로 변환
            //resultMap.put("totalCount", likesService.countLikeByBoard(boardId));
        }

        // Map에 JSON 형태로 담긴 데이터를 Response 해줌
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

