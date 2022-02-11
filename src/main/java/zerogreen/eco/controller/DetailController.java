package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.detail.ReviewImage;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.DetailReviewService;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.detail.ReviewImageService;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class DetailController {

    private final StoreMemberService storeMemberService;
    private final LikesService likesService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ReviewImageService reviewImageService;
    private final DetailReviewService detailReviewService;

/*
    @ModelAttribute("memberReview")
    public MemberReviewDto mReview() {
        MemberReviewDto mReview = new MemberReviewDto();
        return mReview;
    }
*/


    //상세페이지
    @GetMapping("/page/detail/{sno}")
    public String detail(@PathVariable("sno") Long sno, Model model, RequestPageSortDto requestPageSortDto,
                         @ModelAttribute("review") DetailReviewDto reviewDto,
                         @PrincipalUser BasicUser basicUser,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException{

        //스토어 데이터 + 회원/비회원
        StoreDto storeDto = storeMemberService.getStore(sno);
        log.info("?????Controller " + sno);
        log.info("<<<<< " + storeDto.getCount());

        List<DetailReviewDto> result = detailReviewService.findByStore(sno);

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

        model.addAttribute("memberReview", result);

        //가게별 멤버리뷰 카운팅
        Long cnt2 = detailReviewService.cntMemberReview(sno);
        if (cnt2 != null) {
            model.addAttribute("cnt2", cnt2);
        }

        return "page/detail";
    }


        //상세페이지 멤버리뷰 리스트
/*
        Pageable pageable = requestPageSortDto.getPageableSort(Sort.by("id").descending());
        Page<DetailReviewDto> reviewList = detailReviewService.getReviewList(pageable, sno);
        PagingDto memberReview = new PagingDto(reviewList);
        model.addAttribute("memberReview",memberReview);
*/

/*
        //이미지
        if (reviewImageService.findByStore(sno).size() > 0) {
            model.addAttribute("reviewImageList", reviewImageService.findByStore(sno));
        }
*/

/*
        //여기 rno 구해야함
        if (reviewImageService.findByReview(memberReviewDto.getRno()).size() > 0) {
            model.addAttribute("image", reviewImageService.findByReview(memberReviewDto.getRno()));
        }
        log.info("vvvvvvvrno " + memberReviewDto.getRno());
*/


    //save reviews
    @PostMapping("/page/detail/addReview/{sno}")
    public String saveReview(@PathVariable("sno") Long sno, Model model, RequestPageSortDto requestPageSortDto,
                            @ModelAttribute("review") DetailReviewDto reviewDto,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

//        List<ReviewImage> reviewImages = reviewImageService.reviewImageFiles(reviewDto.getImageFiles());
//        detailReviewService.saveReview(reviewDto.getReviewText(), sno, principalDetails.getBasicUser(), reviewImages);
        detailReviewService.saveReview(reviewDto.getReviewText(), sno, principalDetails.getBasicUser());

        List<DetailReviewDto> saveResult = detailReviewService.findByStore(sno);
        model.addAttribute("memberReview", saveResult);

        return "page/detail :: #reviewList";
    }


/*
    //멤버 리뷰 db
    @PostMapping("/page/detail/{sno}")
    public String addReview(@PathVariable("sno") Long sno,
                            @Validated @ModelAttribute("review") ReviewRequestDto reviewRequestDto, BindingResult bindingResult,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        List<ReviewImage> reviewImages = reviewImageService.reviewImageFiles(reviewRequestDto.getImageFiles());

        Long rno = reviewService.saveReview(reviewRequestDto, principalDetails.getBasicUser(),sno, reviewImages);

        //return rno;
        return "redirect:/page/detail/"+sno;
    }
*/


/*
    //멤버리뷰 삭제
    @ResponseBody
    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Long> remove(@PathVariable("id") Long id) {
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
*/


    @ResponseBody
    @GetMapping("/page/detail/images/{filename}")
    private Resource getReviewImages(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + reviewImageService.getFullPath(filename));
    }

    //좋아요
    @ResponseBody
    @PostMapping("/detailLikes/{sno}")
    public ResponseEntity<Map<String, Long>> detailLikes(@PathVariable("sno") Long sno,
                                                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Map<String, Long> resultMap = new HashMap<>();

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