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

    @ResponseBody
    @PostMapping("/detailLikes/{sno}")
    public ResponseEntity<Map<String, Long>> detailLikes(@PathVariable("sno") Long sno,
                                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Map<String, Long> resultMap = new HashMap<>();

        // 해당 회원이 좋아요를 누른 적이 있는지 확인 -> 있으면 1, 없으면 0
        Long cntMemberLike = likesService.cntMemberLike(sno, principalDetails.getId());

        // JSON 형태로 View에 데이터를 전달하기 위해서 key:value로 변경
        resultMap.put("memberCnt", cntMemberLike);

        if (cntMemberLike <= 0) {
            likesService.addLikes(sno, principalDetails.getBasicUser());
            resultMap.put("totalCount", likesService.cntLikes(sno));

        } else {
            likesService.removeLike(sno, principalDetails.getId());
            resultMap.put("totalCount", likesService.cntLikes(sno));
        }

        // Map에 JSON 형태로 담긴 데이터를 Response 해줌
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

