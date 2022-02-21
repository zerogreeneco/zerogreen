package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerogreen.eco.dto.api.ApiReturnDto;
import zerogreen.eco.dto.community.CommunityReplyDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.community.CommunityReplyService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommunityApiController {
    private final CommunityBoardService boardService;
    private final CommunityReplyService replyService;

    @RequestMapping(value = "/api/communityList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiReturnDto communityHomeFormV2(@RequestParam(value = "category", required = false) Category category,
                                            RequestPageSortDto requestPageDto, Model model) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("title").descending());

        log.info("CATEGORY={}", category);

        if (category == null) {
            Instant start = Instant.now();
            Slice<CommunityResponseDto> allCommunityBoard = boardService.findAllCommunityBoard(pageable);
            Instant close = Instant.now();
            Long timeElapsed = Duration.between(start, close).toMillis();
            log.info("TIME TEST={}", timeElapsed);
            int size = allCommunityBoard.getSize();
            return new ApiReturnDto<>(size, allCommunityBoard);
        } else {
            Slice<CommunityResponseDto> sorByCategory = boardService.findByCategory(pageable, category);
            int size = sorByCategory.getSize();
            return new ApiReturnDto<>(size, sorByCategory);
        }
    }

    @RequestMapping(value = "/api/communityReplyList", produces = MediaType.APPLICATION_JSON_VALUE)
    private ApiReturnDto communityReplyList(@RequestParam(value = "boardId") Long boardId, Model model) {
        List<CommunityReplyDto> replyList = replyService.findReplyByBoardId(boardId);
        int size = replyList.size();
        return new ApiReturnDto<>(size, replyList);
    }
}
