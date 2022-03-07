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
import zerogreen.eco.dto.detail.ReviewImageDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.DetailReviewService;
import zerogreen.eco.service.detail.ReviewImageService;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.store.StoreImageService;
import zerogreen.eco.service.store.StoreMenuService;
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

    private final StoreMemberService storeMemberService;
    private final StoreMenuService storeMenuService;
    private final StoreImageService storeImageService;
    private final DetailReviewService detailReviewService;
    private final ReviewImageService reviewImageService;
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

    //내정보 메인
    @GetMapping("/myInfo")
    public String storeMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model) {

        StoreDto info = storeMemberService.getStore(principalDetails.getId());
        model.addAttribute("info", info);

        //리뷰
        List<DetailReviewDto> review = detailReviewService.findByStore(principalDetails.getId());
        model.addAttribute("review", review);
        Collections.reverse(review);

        //리뷰 이미지
        List<ReviewImageDto> reviewImages = reviewImageService.findByStore(principalDetails.getId());
        model.addAttribute("reviewImageList", reviewImages);
        Collections.reverse(reviewImages);

//        List<DetailReviewDto> reviews = storeMemberService.getReviewByStore(principalDetails.getId());
//        model.addAttribute("review", reviews);
//        log.info("KGH" + reviews);
//        Collections.reverse(reviews);

        return "store/myInfo";
    }

    //가게 정보 수정 페이지
    @GetMapping("/update")
    public String updateStoreInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  StoreDto storeDto, Model model) {

        StoreDto info = storeMemberService.storeInfo(principalDetails.getBasicUser().getId(), storeDto);
        model.addAttribute("storeInfo", info);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        List<StoreDto> storeImageList = storeImageService.getImageByStore(principalDetails.getId());
        model.addAttribute("storeImageList", storeImageList);

        return "store/updateInfo";
    }

    //가게 정보 수정
    @PostMapping("/update")
    public String updateStoreInfo(@Validated @ModelAttribute("storeInfo") StoreDto storeDto, BindingResult bindingResult,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            List<StoreDto> storeImageList = storeImageService.getImageByStore(principalDetails.getId());
            model.addAttribute("storeImageList", storeImageList);
            List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
            model.addAttribute("tableList", tableList);

            return "store/updateInfo";
        }

        List<MultipartFile> attachedFiles = storeDto.getUploadFiles();

        log.info("KGH"+attachedFiles.get(0).getContentType());
            for (MultipartFile uploadFile : attachedFiles) {
                if (!uploadFile.getContentType().startsWith("image")
                && !uploadFile.getContentType().endsWith("octet-stream")) {
                    List<StoreDto> storeImageList = storeImageService.getImageByStore(principalDetails.getId());
                    model.addAttribute("storeImageList", storeImageList);
                    List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
                    model.addAttribute("tableList", tableList);

                    bindingResult.reject("notImageFile", "사진을 첨부해주세요");

                    return "store/updateInfo";
                }
            }

        //이미지 로컬 저장
        List<StoreImageFile> storeImageFiles = fileService.storeImageFiles(storeDto.getUploadFiles(), storeDto.getStoreName());
        //이미지 DB 저장, 회원정보 수정
        storeMemberService.updateStore(principalDetails.getId(), storeDto, storeImageFiles);

        return "redirect:/stores/myInfo";
    }

    //비건 등급 포함 테이블 등록
    @PostMapping("/update/gradeTable")
    public String updateGradeList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        VegetarianGrade vegetarianGrade = VegetarianGrade.valueOf(request.getParameter("grade"));

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price, vegetarianGrade);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #grade-table";
    }

    //테이블 등록
    @PostMapping("/update/table")
    public String updateMenuList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model, HttpServletRequest request) {

        String name = request.getParameter("name");
        String price = request.getParameter("price");

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #list-table";
    }

    //메뉴 삭제
    @DeleteMapping("update/grade/delete")
    public String gradeDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model, HttpServletRequest request) {

        Long id = Long.valueOf(request.getParameter("id"));
        storeMenuService.deleteMenu(id);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #grade-table";
    }

    //상품 삭제
    @DeleteMapping("update/table/delete")
    public String tableDelete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model, HttpServletRequest request) {

        Long id = Long.valueOf(request.getParameter("id"));
        storeMenuService.deleteMenu(id);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #list-table";
    }

    //스토어 이미지 불러오기
    @ResponseBody
    @GetMapping("/update/image/{storeName}{storeFileName}")
    private Resource getStoreImages(@PathVariable("storeFileName") String storeFileName,
                                    @PathVariable("storeName") String storeName) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPathImage(storeFileName, storeName));
    }

    //이미지 삭제
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


    //회원 정보 수정
    @GetMapping("/account")
    public String updateAccount() {

        return "store/updateStoreMember";
    }
}
