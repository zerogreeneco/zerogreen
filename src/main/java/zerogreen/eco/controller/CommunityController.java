package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.community.CommunityRequestDto;
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

    /* 커뮤티니 메인 화면 */
    @GetMapping(value = {"", "/"})
    public String communityHomeForm(@RequestParam(value = "category", required = false) String category,
                                    @ModelAttribute("communityList") CommunityRequestDto requestDto) {

        log.info("CATEGORY={}", category);

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

        return "redirect:/";
    }
}
