package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.MemberService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MyInfoController {

    private final LikesService likesService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping("/member/memberMyInfo")
    public String myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model) {

        //회원별 남긴 리뷰 수
        model.addAttribute("reviewCount", reviewService.countReviewByUser(principalDetails.getId()));
        //회원별 좋아요 수
        model.addAttribute("likesCount", likesService.countLikesByUser(principalDetails.getId()));
        //회원 닉네임
        model.addAttribute("profile", memberService.findById(principalDetails.getId()).get());
        //회원별 리뷰 남긴 가게 리스트
        List<MemberReviewDto> review =  reviewService.getReviewByUser(principalDetails.getId());
        //review.sort(Collections.reverseOrder());
        model.addAttribute("listOfReview",review);
        //회원별 찜한 가게 리스트 (memberMyInfo)
        List<LikesDto> likes =  likesService.getLikesByUser(principalDetails.getId());
        model.addAttribute("listOfLikes",likes);


        return "member/memberMyInfo";
    }



}
