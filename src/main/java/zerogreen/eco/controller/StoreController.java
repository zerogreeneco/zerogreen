package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreMemberService storeMemberService;

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

    @GetMapping("/storeMyInfo")
    public String storeMyInfo(){

        return "store/storeMyInfo";
    }

}
