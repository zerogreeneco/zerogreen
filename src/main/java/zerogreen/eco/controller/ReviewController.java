package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.paging.PagingDto;
import zerogreen.eco.dto.paging.RequestPageDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreMemberService storeMemberService;

    @ResponseBody
    @PostMapping("/addReview/{id}")
    public Long addReview(@RequestBody MemberReviewDto memberReviewDto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
            Long result = reviewService.saveReview(memberReviewDto);
            return result;
        }

    @GetMapping("/reviewList/{id}")
    public Page<MemberReviewDto> memberReviewList(Model model, RequestPageDto requestPageDto) {
        Pageable pageable = requestPageDto.getPageable();
        log.info("aaaaaaaa11111 "+pageable );

        Page<MemberReviewDto> reviewList = reviewService.getMemberReviewList(pageable);
        log.info("aaaaaaaa22222 "+reviewList );

        PagingDto memberReview = new PagingDto(reviewList);

        model.addAttribute("memberReview",memberReview);
        return reviewList;
    }

    }
