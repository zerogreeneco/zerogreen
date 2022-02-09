package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.file.TestImageUploadDto;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.StoreMemberService;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ListController {

    private final FileService fileService;
    private final StoreMemberService storeMemberService;

    @ModelAttribute("storeTypes")
    private StoreType[] storeTypes() {
        StoreType[] storeTypes = StoreType.values();
        return storeTypes;
    }

    @GetMapping("/shop/list")
    public String shopList(Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("storeName").descending());
        model.addAttribute("list", storeMemberService.getShopList(pageable));

        return "page/shopList";
    }

    @GetMapping("/food/list")
    public String foodList(@RequestParam(value = "type", required = false) StoreType storeType,
                           Model model, RequestPageSortDto requestPageDto) {

//        try {
//            if ("L".equals(storeType)) {
//                Pageable pageable = requestPageDto.getPageableSort(Sort.by("storeName").descending());
//                model.addAttribute("list", storeMemberService.getFoodTypeList(pageable, storeType));
//            }
//        } catch (Exception e) {
//
//        }

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("storeName").descending());
        if (storeType == null) {
            model.addAttribute("list", storeMemberService.getFoodList(pageable));
        } else {
            model.addAttribute("list", storeMemberService.getFoodTypeList(pageable, storeType));
        }

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
