package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreMemberService storeMemberService;

    @ModelAttribute("vegan")
    private VegetarianGrade[] vegetarianGrades() {
        VegetarianGrade[] vegetarianGrades = VegetarianGrade.values();
        return vegetarianGrades;
    }

    @GetMapping("/update")
    public String updateStoreInfo(){
//        StoreDto update = storeMemberService.updateStore(principalDetails.getUsername(), storeDto);
//        model.addAttribute("store", update);
        return "store/updateStoreInfo";
    }

    @GetMapping("/storeMyInfo")
    public String storeMyInfo(){

        return "store/storeMyInfo";
    }

}
