package zerogreen.eco.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.ApiReturnDto;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.dto.paging.RequestPageDto;
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

    @GetMapping("/api/communityList")
    @ResponseBody
    public ApiReturnDto communityHomeFormV2(@RequestParam(value = "category", required = false) Category category,
                                            RequestPageSortDto requestPageDto, Model model) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("title").descending());

        log.info("CATEGORY={}", category);

        if (category == null) {
            Slice<CommunityResponseDto> allCommunityBoard = boardService.findAllCommunityBoard(pageable);
            return new ApiReturnDto<>(allCommunityBoard);
        } else {
            Slice<CommunityResponseDto> byCategory = boardService.findByCategory(pageable, category);
            return new ApiReturnDto<>(byCategory);
        }
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
    private String detailView(@PathVariable("boardId") Long boardId, Model model) {

        // 게시글 조회수 증가
//        boardService.boardCount(boardId);

        CommunityResponseDto detailView = boardService.findDetailView(boardId);
        log.info("COUNTCOUNTCOUNT={}",detailView.getCount());
        model.addAttribute("detailView", detailView);

        return "community/communityDetailView";
    }
}
