package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.store.StoreMenuService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreMemberService storeMemberService;
    private final StoreMenuService storeMenuService;

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

//    내정보
    @GetMapping("/myInfo")
    public String storeMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                              Model model){

        StoreDto info = storeMemberService.getStore(principalDetails.getId());
        model.addAttribute("info",info);

        return "store/myInfo";
    }

    @GetMapping("/update")
    public String updateStoreInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  Model model, StoreDto storeDto){

        StoreDto info = storeMemberService.storeInfo(principalDetails.getBasicUser().getId(), storeDto);
        model.addAttribute("storeInfo", info);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo";
    }

    @PostMapping("/update")
    public String updateStoreInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @Validated @ModelAttribute("storeInfo") StoreDto storeDto,
                                  BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError allError : allErrors) {
                log.info("ERRORCODE={}", allError);
            }
            return "store/updateInfo";
        }

        storeMemberService.updateStore(principalDetails.getId(), storeDto);

        return "redirect:/stores/myInfo";
    }

    @PostMapping("/update/gradeTable")
    public String updateGradeList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model, HttpServletRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "store/updateInfo";
        }

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        VegetarianGrade vegetarianGrade = VegetarianGrade.valueOf(request.getParameter("grade"));

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price, vegetarianGrade);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #grade-table";
    }

    @PostMapping("/update/table")
    public String updateMenuList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model, HttpServletRequest request) {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        storeMenuService.updateStoreMenu(principalDetails.getId(), name, price);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #list-table";
    }

    @DeleteMapping("update/table/delete")
    public String delete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model, HttpServletRequest request){

        Long id = Long.valueOf(request.getParameter("id"));
        storeMenuService.deleteMenu(id);

        List<StoreMenuDto> tableList = storeMenuService.getStoreMenu(principalDetails.getId());
        model.addAttribute("tableList", tableList);

        return "store/updateInfo :: #list-table";
    }
}
