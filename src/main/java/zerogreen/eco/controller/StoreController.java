package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.user.MemberService;

@Controller
@Slf4j
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    @GetMapping("/update")
    public String updateStoreInfo(){

        return "store/updateStoreInfo";
    }

    @GetMapping("/storeMyInfo")
    public String storeMyInfo(){

        return "store/storeMyInfo";
    }

}
