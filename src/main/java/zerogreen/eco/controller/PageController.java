package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PageController {

    private final StoreMemberService storeMemberService;

    @GetMapping("/page/detail")
    public void detail(Long id, Model model,
                       @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : basicUser" ) BasicUser basicUser){
        StoreDto storeDto = storeMemberService.getStore(id);
        if (basicUser == null) {
            model.addAttribute("getStoreTemp",storeDto);
        } else {
            model.addAttribute("getStoreTemp",storeDto);
            model.addAttribute("member", basicUser.getUserRole());
        }
    }

    @GetMapping("/member/memberMyInfo")
    public void connect2(){

    }

}