package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.file.FileService;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityBoardService boardService;
    private final FileService fileService;

    @ModelAttribute("category")
    public Category[] categories() {
        Category[] categories = Category.values();
        return categories;
    }

    /* 커뮤티니 메인 화면 및 카테고리 페이징 */
    @GetMapping("")
    public String communityHomeForm(@RequestParam(value = "category", required = false) Category category,
                                    RequestPageSortDto requestPageDto, Model model) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("title").descending());

        log.info("CATEGORY={}", category);

        if (category == null) {
            model.addAttribute("communityList", boardService.findAllCommunityBoard(pageable));
        } else {
            model.addAttribute("communityList", boardService.findByCategory(pageable, category));
        }
        return "community/communityHomeForm";
    }

    /* 커뮤니티 글 작성 */
    @GetMapping("/write")
    public String writeForm(@ModelAttribute("writeForm") CommunityRequestDto dto) {
        return "community/communityRegisterForm";
    }

    @PostMapping("/write")
    public String write(@Validated @ModelAttribute("writeForm") CommunityRequestDto dto,
                        BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        log.info("writer={}", principalDetails.getId());
        log.info("title={}", dto.getTitle());
        log.info("content={}", dto.getText());
        log.info("category={}", dto.getCategory());
        List<BoardImage> storeImages = fileService.boardImageFiles(dto.getImageFiles());

        boardService.boardRegister(dto, (Member) principalDetails.getBasicUser(), storeImages);

        return "redirect:/community";
    }

    /* 게시글 상세보기 */
    @GetMapping("/read/{boardId}")
    public String detailView(@PathVariable("boardId") Long boardId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            model.addAttribute("likeCount", boardService.countLike(boardId, principalDetails.getBasicUser().getId()));
        }

        CommunityResponseDto detailView = boardService.findDetailView(boardId);

        log.info("COUNTCOUNTCOUNT={}", detailView.getCount());
        model.addAttribute("detailView", detailView);

        return "community/communityDetailView";
    }

    /* 좋아요 */
    @PostMapping("/like/{boardId}")
    @ResponseBody
    public ResponseEntity<String> communityLike(@PathVariable("boardId")Long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info("LIKE CONTROL OK");

        int countLike = boardService.countLike(boardId, principalDetails.getId());
        log.info("COUNTLIKE={}", countLike);

        if (countLike <= 0) {
            boardService.insertLike(boardId, principalDetails.getBasicUser());
        } else if (countLike > 0){
            boardService.deleteLike(boardId, principalDetails.getId());
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
