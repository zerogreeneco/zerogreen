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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.detail.StoreReviewDto;
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
    public Long addReview(@Validated @RequestBody MemberReviewDto memberReviewdto, BindingResult bindingResult,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
            Long result = reviewService.saveReview(memberReviewdto);
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
    @ResponseBody
    @PutMapping("/editReview/{rno}")
    public ResponseEntity<Long> modifyReview(@Validated @RequestBody MemberReviewDto memberReviewDto, BindingResult bindingResult,
                                             @PathVariable Long rno){
        reviewService.modifyReview(memberReviewDto);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    //스토어 멤버 리뷰 db
    @ResponseBody
    @PostMapping("/addStoreReview/{rno}")
    public Long addReview(@Validated @RequestBody StoreReviewDto storeReviewDto, BindingResult bindingResult,
                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //log.info("qqqqqq11111: "+ storeReviewDto.getRno());
        //log.info("qqqqqq22222: "+ storeReviewDto.getSno());
        Long result = reviewService.saveStoreReview(storeReviewDto);
        return result;
    }

    //스토어멤버 리뷰 삭제
    @ResponseBody
    @DeleteMapping("/deleteStoreReview/{sno}/{id}")
    public ResponseEntity<Long> deleteStoreReview(@PathVariable Long id) {
        reviewService.deleteStoreReview(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    //스토어멤버 리뷰 수정 ** JSON 형식으로 다시 수정 예정**
    @ResponseBody
    @PutMapping("/modifyStoreReview/{srno}")
    public ResponseEntity<Long> modifyStoreReview(@Validated @RequestBody StoreReviewDto storeReviewDto, BindingResult bindingResult,
                                             @PathVariable Long srno){
        reviewService.modifyStoreReview(storeReviewDto);
        return new ResponseEntity<>(srno, HttpStatus.OK);
    }





}
