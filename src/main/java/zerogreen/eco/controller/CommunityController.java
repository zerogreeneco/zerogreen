package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.dto.community.CommunityRequestDto;
import zerogreen.eco.dto.community.ImageFileDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.dto.search.SearchCondition;
import zerogreen.eco.dto.search.SearchType;
import zerogreen.eco.entity.community.BoardImage;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.community.BoardImageService;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.community.CommunityReplyService;
import zerogreen.eco.service.file.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
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
    private final CommunityReplyService replyService;
    private final BoardImageService boardImageService;

    @ModelAttribute("category")
    public Category[] categories() {
        Category[] categories = Category.values();
        return categories;
    }

    /* 커뮤티니 메인 화면 및 카테고리 페이징 */
    @GetMapping("")
    public String communityHomeForm(@RequestParam(value = "category", required = false) Category category,
                                    RequestPageSortDto requestPageDto, Model model,
                                    SearchType searchType, String keyword) {

        communityPagingList(requestPageDto, model, searchType, keyword, category);
        return "community/communityHomeForm";
    }


    // 더보기
    @PostMapping("")
    public String communityMoreList(@RequestParam("page")Long page,
                                    @RequestParam(value = "category", required = false) Category category,
                                    @RequestParam(value = "searchType", required = false) SearchType searchType,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    RequestPageSortDto requestPageDto, Model model) {

        communityPagingList(requestPageDto, model, searchType, keyword, category);
        return "community/communityHomeForm :: #more-wrapper";
    }

    /* 커뮤니티 글 작성 */
    @GetMapping("/write")
    public String writeForm(@ModelAttribute("writeForm") CommunityRequestDto dto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UserRole userRole = principalDetails.getBasicUser().getUserRole();
        if (!(userRole.equals(UserRole.STORE) || userRole.equals(UserRole.UN_STORE))) {
            return "community/communityRegisterForm";
        } else {
            return "redirect:/community";
        }
    }

    @PostMapping("/write")
    public String write(@Validated @ModelAttribute("writeForm") CommunityRequestDto dto,
                        BindingResult bindingResult,
                        @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        Long boardId = boardService.boardRegister(dto, (Member) principalDetails.getBasicUser(),
                fileService.boardImageFiles(dto.getImageFiles()));

        return "redirect:/community/read/" + boardId;
    }

    /* 게시글 수정 */
    @GetMapping("/{boardId}/modify")
    public String modifyForm(@PathVariable("boardId") Long boardId, Model model) {

        model.addAttribute("writeForm", boardService.boardModifyRequest(boardId));
        if (boardImageService.findByBoardId(boardId).size() > 0) {
            model.addAttribute("images", boardImageService.findByBoardId(boardId));
        }

        return "community/communityModifyForm";
    }

    @PostMapping("/{boardId}/modify")
    public String modifyBoard(@PathVariable("boardId") Long boardId,
                              @ModelAttribute("writeForm") CommunityRequestDto requestDto) throws IOException {
        boardService.boardModify(boardId, requestDto.getCategory(), requestDto.getText());
        List<BoardImage> storeImages = fileService.boardImageFiles(requestDto.getImageFiles());
        for (BoardImage storeImage : storeImages) {
            boardImageService.modifyImage(boardId, storeImage.getStoreFileName(),
                    storeImage.getUploadFileName(), storeImage.getFilePath());
        }

        return "redirect:/community/read/" + boardId;
    }

    // 이미지 삭제
    @DeleteMapping("/{imageId}/imageDelete")
    @ResponseBody
    public ResponseEntity<Map<String, String>> imageDelete(@PathVariable("imageId") Long imageId,
                                                           HttpServletRequest request) {
        HashMap<String, String> resultMap = new HashMap<>();
        String filePath = request.getParameter("filePath");

        // 이미지 PK와 경로를 클라이언트에서 넘겨받아서 DB와 로컬 저장소 모두 삭제
        boardImageService.deleteImage(imageId, filePath);
        resultMap.put("key", "success");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /* 게시글 상세보기 */
    @GetMapping("/read/{boardId}")
    public String detailView(@PathVariable("boardId") Long boardId, Model model,
                             @ModelAttribute("reply") CommunityReplyDto replyDto,
                             @AuthenticationPrincipal PrincipalDetails principalDetails,
                             HttpServletRequest request, HttpServletResponse response,
                             HttpSession session) {

        if (principalDetails != null) {
            model.addAttribute("likeCount", boardService.countLike(boardId, principalDetails.getBasicUser().getId()));
            session.setAttribute("loginUser", principalDetails.getBasicUser().getUsername());
        }
        model.addAttribute("myId",principalDetails.getUsername());

        model.addAttribute("detailView", boardService.findDetailView(boardId, request, response));
        log.info("yjyjyjyjyjyjyjyj"+boardService.findDetailView(boardId, request, response));
        model.addAttribute("replyList", replyService.findReplyByBoardId(boardId));

        List<ImageFileDto> imageList = boardImageService.findByBoardId(boardId);
        if (imageList.size() > 0) {
            model.addAttribute("images", imageList);
        }

        return "community/communityDetailView";
    }

    /*
     * 이미지 출력
     * */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource getImages(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    /*
     * 게시글 삭제
     * */
    @PostMapping("/{boardId}/delete")
    public String boardDelete(@PathVariable("boardId") Long boardId) {
        boardService.boardDelete(boardId);
        return "redirect:/community";
    }

    /* 좋아요 */
    @PostMapping("/like/{boardId}")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> communityLike(@PathVariable("boardId") Long boardId,
                                                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Map<String, Integer> resultMap = new HashMap<>();

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

    /*
     * 댓글
     * */
    @PostMapping("/{boardId}/reply")
    public String replySend(@PathVariable("boardId") Long boardId, Model model,
                            @ModelAttribute("reply") CommunityReplyDto replyDto,
                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        replyService.replySave(replyDto.getText(), boardId, principalDetails.getBasicUser());

        List<CommunityReplyDto> replyByBoardId = replyService.findReplyByBoardId(boardId);
        for (CommunityReplyDto communityReplyDto : replyByBoardId) {
            log.info("REPLLLL<<<<<={}",communityReplyDto);
        }
        model.addAttribute("replyList", replyByBoardId);

        return "community/communityDetailView :: #review-table";
    }

    /*
     * 댓글 수정
     * */
    @PostMapping("/{boardId}/replyModify/{replyId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> modifyReply(@PathVariable("boardId") Long boardId, @PathVariable("replyId") Long replyId,
                                                           HttpServletRequest request) {
        Map<String, String> resultMap = new HashMap<>();
        String text = request.getParameter("text");

        log.info("BOARDID={}", boardId);
        log.info("REPLYID={}", replyId);
        log.info("TEXT={}", text);

        replyService.modifyReply(replyId, text);

        resultMap.put("result", "success");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /*
     * 댓글 삭제
     * */
    @DeleteMapping("/{replyId}/delete")
    public String deleteReply(@PathVariable("replyId") Long replyId, Model model, HttpServletRequest request) {

        String boardId = request.getParameter("boardId");
        replyService.deleteReply(replyId);

        model.addAttribute("replyList", replyService.findReplyByBoardId(Long.valueOf(boardId)));

        return "community/communityDetailView :: #review-table";
    }

    /*
     * 대댓글
     * */
    @PostMapping("/{boardId}/{replyId}/nestedReply")
    public String nestedReplySend(@PathVariable("boardId") Long boardId, @PathVariable("replyId") Long replyId,
                                  @ModelAttribute("nestedReplyForm") CommunityReplyDto replyDto,
                                  Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {

        String text = request.getParameter("text");
        replyService.nestedReplySave(text, boardId, principalDetails.getBasicUser(), replyId);

        List<CommunityReplyDto> replyByBoardId = replyService.findReplyByBoardId(boardId);
        for (CommunityReplyDto communityReplyDto : replyByBoardId) {
            log.info("REPLY LIST!!!!!!!!={}", communityReplyDto);
        }
        model.addAttribute("replyList", replyByBoardId);

        return "community/communityDetailView :: #review-table";
    }

    // Paging List
    private void communityPagingList(RequestPageSortDto requestPageDto, Model model, SearchType searchType, String keyword, Category category) {
        Pageable pageable = requestPageDto.getPageableSort(Sort.by("createdDate").descending());

        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        if (category == null) {
            if (searchType == null) {
                model.addAttribute("communityList", boardService.findAllCommunityBoard(pageable));
            } else {
                model.addAttribute("communityList", boardService.findAllCommunityBoard(pageable, new SearchCondition(keyword, searchType)));
            }
        } else {
            model.addAttribute("communityList", boardService.findByCategory(pageable, category));
        }
    }
}