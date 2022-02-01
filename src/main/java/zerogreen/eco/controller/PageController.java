package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.paging.PagingDto;
import zerogreen.eco.dto.paging.RequestPageDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.StoreMemberService;

import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PageController {

    private final StoreMemberService storeMemberService;
    private final LikesService likesService;
    private final ReviewService reviewService;

    @GetMapping("/page/detail")
    public void detail(Long id, Model model, LikesDto likesDto, RequestPageDto requestPageDto,
                       MemberReviewDto memberReviewDto,
                       @PrincipalUser BasicUser basicUser) throws IOException{
        //스토어 데이터 + 회원/비회원
        StoreDto storeDto = storeMemberService.getStore(id);
        if (basicUser == null) {
            model.addAttribute("getStore",storeDto);
        } else {
            model.addAttribute("getStore",storeDto);
            model.addAttribute("member", basicUser.getUsername());
        }

        //가게별 라이크 카운팅
        Long cnt = likesService.cntLikes(likesDto);
        if (cnt != null) {
            model.addAttribute("cnt",cnt);
        }

        //가게별 멤버리뷰 카운팅
        Long cnt2 = reviewService.cntMemberReview(memberReviewDto);
        if (cnt2 != null) {
            model.addAttribute("cnt2",cnt2);
        }

        //라이크 데이터 ** 수정예정 **
        LikesDto result;
        try {
            result = likesService.liking(id, basicUser.getUsername());
            model.addAttribute("liking", result);
            log.info("xxxxx11111: "+ result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/member/memberMyInfo")
    public void connect2(){

    }

}