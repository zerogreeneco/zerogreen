package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.dto.store.StoreUpdateDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.store.StoreImageService;
import zerogreen.eco.service.store.StoreMenuService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final BasicUserService basicUserService;
    private final StoreMemberService storeMemberService;
    private final StoreMenuService storeMenuService;
    private final StoreImageService storeImageService;
    private final FileService fileService;

    @ModelAttribute("storeTypes")
    private StoreType[] storeTypes() {
        StoreType[] storeTypes = StoreType.values();
        return storeTypes;
    }

    @ModelAttribute("vegan")
    private VegetarianGrade[] vegetarianGrades() {
        VegetarianGrade[] vegetarianGrades = VegetarianGrade.values();
        return vegetarianGrades;
    }

    //????????? ??????
    @GetMapping("/myInfo")
    public String storeMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model) {

        model.addAttribute("info", storeMemberService.getStore(principalDetails.getId()));

        List<DetailReviewDto> reviews = storeMemberService.getReviewByStore(principalDetails.getId());
        model.addAttribute("review", reviews);
        Collections.reverse(reviews);

        return "store/myInfo";
    }

    //?????? ?????? ?????? ?????????
    @GetMapping("/update/info")
    public String updateStoreInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  StoreDto storeDto, Model model) {

        model.addAttribute("storeInfo",
                storeMemberService.getStoreInfo(principalDetails.getBasicUser().getId(), storeDto));
        model.addAttribute("tableList", storeMenuService.getStoreMenu(principalDetails.getId()));
        model.addAttribute("storeImageList", storeImageService.getImageByStore(principalDetails.getId()));

        return "store/updateInfo";
    }

    //?????? ?????? ??????
    @PostMapping("/update/info")
    public String updateStoreInfo(@Validated @ModelAttribute("storeInfo") StoreDto storeDto, BindingResult bindingResult,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) throws IOException {

        List<StoreDto> storeImageList = storeImageService.getImageByStore(principalDetails.getId());
        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("storeImageList", storeImageList);
            model.addAttribute("tableList", tableList);

            return "store/updateInfo";
        }

        List<MultipartFile> attachedFiles = storeDto.getUploadFiles();

        for (MultipartFile uploadFile : attachedFiles) {
            if (!uploadFile.getContentType().startsWith("image")
                    && !uploadFile.getContentType().endsWith("octet-stream")) {
                model.addAttribute("storeImageList", storeImageList);
                model.addAttribute("tableList", tableList);

                bindingResult.reject("notImageFile", "????????? ??????????????????");

                return "store/updateInfo";
            }
        }

        //????????? ?????? ??????
        List<StoreImageFile> storeImageFiles = fileService.storeImageFiles(storeDto.getUploadFiles(), storeDto.getStoreName());
        //????????? DB ??????, ???????????? ??????
        storeMemberService.updateStore(principalDetails.getId(), storeDto, storeImageFiles);

        return "redirect:/stores/myInfo";
    }

    //?????? ?????? ?????? ????????? ??????
    @PostMapping("/update/gradeTable")
    public String updateGradeList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        VegetarianGrade vegetarianGrade = VegetarianGrade.valueOf(request.getParameter("grade"));

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price, vegetarianGrade);

        model.addAttribute("tableList", storeMenuService.getStoreMenu(principalDetails.getId()));

        return "store/updateInfo :: #grade-table";
    }

    //????????? ??????
    @PostMapping("/update/table")
    public String updateMenuList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model, HttpServletRequest request) {

        String name = request.getParameter("name");
        String price = request.getParameter("price");

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price);

        model.addAttribute("tableList", storeMenuService.getStoreMenu(principalDetails.getId()));

        return "store/updateInfo :: #list-table";
    }

    //?????? ??????
    @DeleteMapping("update/grade/delete")
    public String gradeDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model, HttpServletRequest request) {

        Long id = Long.valueOf(request.getParameter("id"));
        storeMenuService.deleteMenu(id);

        model.addAttribute("tableList", storeMenuService.getStoreMenu(principalDetails.getId()));

        return "store/updateInfo :: #grade-table";
    }

    //?????? ??????
    @DeleteMapping("update/table/delete")
    public String tableDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model, HttpServletRequest request) {

        Long id = Long.valueOf(request.getParameter("id"));
        storeMenuService.deleteMenu(id);

        model.addAttribute("tableList", storeMenuService.getStoreMenu(principalDetails.getId()));

        return "store/updateInfo :: #list-table";
    }

    //????????? ????????? ????????????
    @ResponseBody
    @GetMapping("/update/image/{storeName}{storeFileName}")
    private Resource getStoreImages(@PathVariable("storeFileName") String storeFileName,
                                    @PathVariable("storeName") String storeName) throws MalformedURLException {

        return new UrlResource("file:" + fileService.getFullPathImage(storeFileName, storeName));
    }

    //????????? ??????
    @DeleteMapping("update/img/delete")
    @ResponseBody
    public ResponseEntity<Map<String, String>> imgDelete(HttpServletRequest request) {

        HashMap<String, String> resultMap = new HashMap<>();
        Long id = Long.valueOf(request.getParameter("id"));
        String filePath = request.getParameter("filePath");
        String thumb = request.getParameter("thumb");

        storeImageService.deleteImg(id, filePath, thumb);

        resultMap.put("key", "success");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    //?????? ?????? ??????
    @GetMapping("/update/account")
    public String storeAccount(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
                               StoreUpdateDto storeUpdateDto, PasswordUpdateDto passwordUpdateDto) {
        model.addAttribute("member",
                basicUserService.getStoreMember(principalDetails.getBasicUser().getId(), storeUpdateDto));
        model.addAttribute("password", passwordUpdateDto);
        return "store/updateStoreMember";
    }

    @PostMapping("/update/account")
    public String updateAccount(@Validated @ModelAttribute("member") StoreUpdateDto storeUpdateDto, BindingResult bindingResult,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // ?????? ????????? ?????? (Null ?????? ???)
        if (bindingResult.hasErrors()) {

            return "store/updateStoreMember";
        }
        basicUserService.updateStoreMember(principalDetails.getId(), storeUpdateDto);

        return "redirect:/stores/myInfo";
    }
}
