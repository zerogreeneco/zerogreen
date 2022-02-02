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
import zerogreen.eco.dto.ApiReturnDto;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.service.community.CommunityBoardService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommunityApiController {
    private final CommunityBoardService boardService;

    @RequestMapping(value = "/api/communityList", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
