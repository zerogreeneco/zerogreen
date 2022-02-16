package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.userentity.StoreMenu;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.store.StoreMenuService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/store")
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

    @GetMapping("/update")
    public String updateStoreInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  Model model, StoreDto storeDto){
        StoreDto update = storeMemberService.updateStore(principalDetails.getBasicUser().getId(), storeDto);
        model.addAttribute("store", update);

        return "store/updateStoreInfo";
    }

    @RequestMapping(value = "/update/table", method = RequestMethod.POST)
    @ResponseBody
    public String updateMenuList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model, StoreMenuDto storeMenuDto, HttpServletRequest request) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        VegetarianGrade vegetarianGrade = VegetarianGrade.valueOf(request.getParameter("grade"));
       storeMenuService.updateStoreMenu(principalDetails.getId(), name, price, vegetarianGrade);

        return "store/updateStoreInfo :: #list-table";
    }

    @GetMapping("/storeMyInfo")
    public String storeMyInfo(){

        return "store/storeMyInfo";
    }

}
