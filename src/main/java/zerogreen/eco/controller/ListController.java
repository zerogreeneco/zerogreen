package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.file.TestImageUploadDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.StoreMemberService;
import zerogreen.eco.dto.paging.RequestPageSortDto;


import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ListController {

    private final FileService fileService;
    private final StoreMemberService storeMemberService;


    @GetMapping("/shop/list")
    public String shopList(Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("storeName").descending());
        model.addAttribute("list", storeMemberService.getShopList(pageable));

        return "page/shopList";
    }

    @GetMapping("/food/list")
    public String foodList(Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("storeName").descending());
        model.addAttribute("list", storeMemberService.getFoodList(pageable));

        return "page/foodList";
    }

    @GetMapping("/store/storeInfo")
    public String storeInfoForm(@ModelAttribute("upload") TestImageUploadDto uploadDto) {

        return "store/storeInfoForm";
    }

    /*
    * 이미지 업로드 테스트
    * */
    @PostMapping("/store/storeInfo/imageUpload")
    public String saveImage(@ModelAttribute("upload") TestImageUploadDto uploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        List<StoreImageFile> storeImageFiles = fileService.storeImageFiles(uploadDto.getFiles(), uploadDto.getStoreName());

        log.info("MEMBERID={}", principalDetails.getId());
        log.info("MEMBER.image={}", uploadDto.getFiles());
        log.info("MEMBER.storeName={}", uploadDto.getStoreName());

        storeMemberService.imageSave(principalDetails.getId(), storeImageFiles);

        return "redirect:/";
    }

}
