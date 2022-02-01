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
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.StoreMemberService;

import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;
    private final StoreMemberService storeMemberService;

    //멤버 리뷰 db
    @ResponseBody
    @PostMapping("/addReview/{id}")
    public Long addReview(@RequestBody MemberReviewDto memberReviewDto,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
            Long result = reviewService.saveReview(memberReviewDto);
            return result;
    }

    //멤버리뷰 삭제
    @ResponseBody
    @DeleteMapping("/deleteReview/{sno}/{id}")
    public ResponseEntity<Long> remove(@PathVariable Long id) {
        reviewService.remove(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    //멤버리뷰 수정 ** JSON 형식으로 다시 수정 예정**
    @PutMapping("/editReview/{sno}/{rno}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long rno,
                                             @RequestBody MemberReviewDto memberReviewDto){
        reviewService.modifyReview(memberReviewDto);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }




    }
