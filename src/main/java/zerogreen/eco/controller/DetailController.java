package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
import zerogreen.eco.dto.paging.PagingDto;
import zerogreen.eco.dto.paging.RequestPageDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.StoreReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class DetailController {

    private final StoreMemberService storeMemberService;
    private final LikesService likesService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    //상세페이지
    @GetMapping("/page/detail/{sno}")
    public String detail(Long id,@PathVariable("sno") Long sno, Model model, RequestPageSortDto requestPageSortDto,
                       MemberReviewDto memberReviewDto,
                       @PrincipalUser BasicUser basicUser, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException{

        //스토어 데이터 + 회원/비회원
        StoreDto storeDto = storeMemberService.getStore(sno);
        log.info("?????Controller " + sno);
        log.info("<<<<< " + storeDto.getCount());

        if (principalDetails == null) {
            model.addAttribute("getStore",storeDto);
        } else {
            model.addAttribute("getStore",storeDto);
            model.addAttribute("member", principalDetails.getUsername());
            //리뷰어쩌구..
            model.addAttribute("rvMember", principalDetails.getId());
            //가게별 개별 라이크
            model.addAttribute("cntLike", likesService.cntMemberLike(sno, principalDetails.getId()));
        }

        //가게별 멤버리뷰 카운팅
        Long cnt2 = reviewService.cntMemberReview(sno);
        if (cnt2 != null) {
            model.addAttribute("cnt2", cnt2);
        }

        //상세페이지 멤버리뷰 리스트
        Pageable pageable = requestPageSortDto.getPageableSort(Sort.by("id").descending());
        Page<MemberReviewDto> reviewList = reviewService.getMemberReviewList(pageable, sno);
        PagingDto memberReview = new PagingDto(reviewList);
        model.addAttribute("memberReview",memberReview);

        return "page/detail";
    }

    //좋아요
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