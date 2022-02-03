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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<BoardImage> storeImages = fileService.boardImageFiles(dto.getImageFiles());

        boardService.boardRegister(dto, (Member) principalDetails.getBasicUser(), storeImages);

        return "redirect:/community";
    }

    /* 게시글 상세보기 */
    @GetMapping("/read/{boardId}")
    public String detailView(@PathVariable("boardId") Long boardId, Model model,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             HttpServletRequest request, HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (principalDetails != null) {
            model.addAttribute("likeCount", boardService.countLike(boardId, principalDetails.getBasicUser().getId()));
        }

        CommunityResponseDto detailView = boardService.findDetailView(boardId, request, response);
        model.addAttribute("detailView", detailView);

        return "community/communityDetailView";
    }

    /* 좋아요 */
    @PostMapping("/like/{boardId}")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> communityLike(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Map<String, Integer> resultMap = new HashMap<>();

        log.info("LIKE CONTROL OK");

        int countLike = boardService.countLike(boardId, principalDetails.getId());

        resultMap.put("count", countLike);

        if (countLike <= 0) {
            boardService.insertLike(boardId, principalDetails.getBasicUser());
            resultMap.put("totalCount", boardService.countLikeByBoard(boardId));
        } else if (countLike > 0) {
            boardService.deleteLike(boardId, principalDetails.getId());
            resultMap.put("totalCount", boardService.countLikeByBoard(boardId));
        }

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
