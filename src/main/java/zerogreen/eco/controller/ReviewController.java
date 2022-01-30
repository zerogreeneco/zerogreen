package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.StoreMember;
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

    @PostMapping("/addReview/{id}")
    @ResponseBody
    public ResponseEntity<Long> addReview(@RequestBody MemberReviewDto memberReviewDto,
                                          @PrincipalUser BasicUser basicUser,
                                          StoreMember storeMember, String storeName,
                                          Long id, String username) {
        if (basicUser != null) {
            MemberReview review = new MemberReviewDto().reviewDto(memberReviewDto);
            log.info("zzzzzzzzzzz1111111111" + review);
            reviewService.saveReview(basicUser.getUsername(), id, review);
            log.info("zzzzzzzzzzz2222222222" + basicUser.getUsername());
            log.info("zzzzzzzzzzz6666666666" + storeMember.getStoreName());
            log.info("zzzzzzzzzzz7777777777" + review);

        }
            return new ResponseEntity<>(HttpStatus.OK);
    }

/*
    public ResponseEntity<Long> addReview(@RequestBody MemberReviewDto memberReviewDto) {
        MemberReview review = new MemberReviewDto().reviewDto(memberReviewDto);
        reviewService.saveReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }
*/

/*
    public String addReview(@RequestBody MemberReviewDto memberReviewDto,
                            RedirectAttributes redirectAttributes) {
        MemberReview review = new MemberReviewDto().reviewDto(memberReviewDto);
        reviewService.saveReview(review);
        return "redirect: page/detail";
    }
*/



    }
