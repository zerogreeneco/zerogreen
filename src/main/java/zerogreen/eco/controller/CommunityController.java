package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.community.CommunityReplyService;
import zerogreen.eco.service.file.FileService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityBoardService boardService;
    private final FileService fileService;
    private final CommunityReplyService replyService;

    @ModelAttribute("category")
    public Category[] categories() {
        Category[] categories = Category.values();
        return categories;
    }

    /* 커뮤티니 메인 화면 및 카테고리 페이징 */
    @GetMapping("")
    public String communityHomeForm(@RequestParam(value = "category", required = false) Category category,
                                    RequestPageSortDto requestPageDto, Model model,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("title").descending());

//        if (principalDetails != null) {
//            model.addAttribute("likeCount", boardService.countLike(boardId, principalDetails.getBasicUser().getId()));
//        }

        log.info("CATEGORY={}", category);

        if (category == null) {
            Slice<CommunityResponseDto> allCommunityBoard = boardService.findAllCommunityBoard(pageable);
            List<CommunityResponseDto> collect = allCommunityBoard.get().collect(Collectors.toList());

            model.addAttribute("communityList", allCommunityBoard);
//            if (principalDetails != null) {
//                for (int i = 0; i < collect.size(); i++) {
//                model.addAttribute("likeCount", boardService.countLike(collect.get(i).getId(), principalDetails.getBasicUser().getId()));
//                }
//            }
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
                        BindingResult bindingResult,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        List<BoardImage> storeImages = fileService.boardImageFiles(dto.getImageFiles());

        boardService.boardRegister(dto, (Member) principalDetails.getBasicUser(), storeImages);

        return "redirect:/community";
    }

    /* 게시글 상세보기 */
    @GetMapping("/read/{boardId}")
    public String detailView(@PathVariable("boardId") Long boardId, Model model,
                             @ModelAttribute("reply") CommunityReplyDto replyDto,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             HttpServletRequest request, HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (principalDetails != null) {
            model.addAttribute("likeCount", boardService.countLike(boardId, principalDetails.getBasicUser().getId()));
        }

        CommunityResponseDto detailView = boardService.findDetailView(boardId, request, response);
        model.addAttribute("detailView", detailView);
        model.addAttribute("replyList", replyService.findReplyByBoardId(boardId));

        return "community/communityDetailView";
    }

    /* 좋아요 */
    @PostMapping("/like/{boardId}")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> communityLike(@PathVariable("boardId") Long boardId,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Map<String, Integer> resultMap = new HashMap<>();

        log.info("LIKE CONTROL OK");

        // 해당 회원이 좋아요를 누른 적이 있는지 확인 -> 있으면 1, 없으면 0
        int countLike = boardService.countLike(boardId, principalDetails.getId());

        // JSON 형태로 View에 데이터를 전달하기 위해서 key:value로 변경
        resultMap.put("count", countLike);

        // 결과에 따라서 insert / delete 쿼리 분기
        if (countLike <= 0) {
            // insert
            boardService.insertLike(boardId, principalDetails.getBasicUser());
            // insert 후에 DB에서 해당 게시물의 전체 좋아요 수 카운트 후 json 형태로 변환
            resultMap.put("totalCount", boardService.countLikeByBoard(boardId));
        } else if (countLike > 0) {
            // delete
            boardService.deleteLike(boardId, principalDetails.getId());
            // delete 후에 DB에서 해당 게시물의 전체 좋아요 수 카운트 후 json 형태로 변환
            resultMap.put("totalCount", boardService.countLikeByBoard(boardId));
        }

        // Map에 JSON 형태로 담긴 데이터를 Response 해줌
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/{boardId}/reply")
    public String replySend(@PathVariable("boardId") Long boardId, Model model,
                            @ModelAttribute("reply") CommunityReplyDto replyDto,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        replyService.replySave(replyDto.getText(),boardId, principalDetails.getBasicUser());

        model.addAttribute("replyList", replyService.findReplyByBoardId(boardId));

        return "community/communityDetailView :: #review-table";
    }
}
